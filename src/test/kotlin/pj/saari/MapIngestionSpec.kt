package pj.saari

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pj.saari.map.createMatrix
import pj.saari.map.dfsMatrixForIslands
import pj.saari.map.parseMapJson
import strikt.api.expect
import strikt.assertions.hasSize
import strikt.assertions.isA
import kotlin.test.assertNotNull

object MapIngestionSpec : Spek({
    describe("Map operations") {
        val mockJson = MapIngestionSpec::class.java.getResource("/mock.json").readText()
        assertNotNull(mockJson)
        val mapResponse = parseMapJson(mockJson)
        it("can parse tiles from Map Json") {
            expect {
                that(mapResponse)
                        .isA<MapResponse>()
                that(mapResponse.attributes.tiles)
                        .hasSize(30)
            }
        }

        it("can find islands from the map") {
            val islands = dfsMatrixForIslands(createMatrix(mapResponse.attributes.tiles))
            expect {
                that(islands.size == 3)
                that(islands.filter { it.id == 1 })
                        .hasSize(1)
                        .and {
                            islands[0].tiles.contains(TileCoord(x = 1, y = 1))
                            islands[0].tiles.contains(TileCoord(x = 2, y = 1))
                            islands[0].tiles.contains(TileCoord(x = 2, y = 2))
                        }
            }
        }

    }
})
