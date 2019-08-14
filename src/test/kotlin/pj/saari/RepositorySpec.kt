package pj.saari

import beans
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import org.springframework.beans.factory.getBean
import org.springframework.context.support.GenericApplicationContext
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import pj.saari.repository.EmbeddedMongoDb
import strikt.api.expectThat


object RepositorySpec : Spek({
    EmbeddedMongoDb()
    val context = GenericApplicationContext().apply {
        beans().initialize(this)
        refresh()
    }
    val template = context.getBean<ReactiveMongoTemplate>()


    describe("Mongo Db Operations") {

        it("It can store and load Islands") {
            val island1 = Island(1)
            island1.tiles.add(TileCoord(3,2))
            template.save(island1).block()

            val island2 = template.findById(2, Island::class.java).block()
            expectThat(island2?.tiles?.contains(TileCoord(3,2)))
        }

    }
})
