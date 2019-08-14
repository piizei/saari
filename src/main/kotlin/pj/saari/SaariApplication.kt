package pj.saari

import beans
import org.springframework.beans.factory.getBean
import org.springframework.context.support.GenericApplicationContext
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.createCollection
import org.springframework.http.server.reactive.HttpHandler
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.server.adapter.WebHttpHandlerBuilder
import pj.saari.repository.EmbeddedMongoDb
import reactor.ipc.netty.http.server.HttpServer
import reactor.ipc.netty.tcp.BlockingNettyContext

class SaariApplication(port: Int = 8080) {
    private val httpHandler: HttpHandler
    private val server: HttpServer
    private var nettyContext: BlockingNettyContext? = null
    private var context: GenericApplicationContext? = null

    init {
        EmbeddedMongoDb()
        context = GenericApplicationContext().apply {
            beans().initialize(this)

            refresh()
        }
        server = HttpServer.create(port)
        httpHandler = WebHttpHandlerBuilder.applicationContext(context!!).build()
    }

    fun startAndAwait() {
        //Create Island collection
        val template = context?.getBean<ReactiveMongoTemplate>()
        template?.createCollection(Island::class)?.block()

        server.startAndAwait(ReactorHttpHandlerAdapter(httpHandler)) { nettyContext = it }

    }

}

fun main(args: Array<String>) {
    SaariApplication().startAndAwait()
}