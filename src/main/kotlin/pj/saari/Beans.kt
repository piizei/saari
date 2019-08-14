import com.mongodb.ConnectionString
import org.springframework.context.support.beans
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory
import org.springframework.web.reactive.function.server.RouterFunctions.toWebHandler
import org.springframework.web.server.adapter.WebHttpHandlerBuilder

fun beans() = beans {
    bean<Routes>()
    bean(WebHttpHandlerBuilder.WEB_HANDLER_BEAN_NAME) {
        toWebHandler(ref<Routes>().router())
    }
    bean {
        ReactiveMongoTemplate(
                SimpleReactiveMongoDatabaseFactory(
                        ConnectionString("mongodb://localhost:27017/test")

                )
        )
    }
    bean {
        IslandHandler(
                ref()
        )
    }
}