import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

class Routes(private val mapHandler: IslandHandler) {

    fun router() = router {
        "/api".nest {
            accept(APPLICATION_JSON).nest {
                POST("/maps", mapHandler::initialize)
                GET("/maps") { ServerResponse.badRequest().build() }
                GET("/islands", mapHandler::findAll)
                GET("/islands/{id}", mapHandler::findOne)

            }
        }
        GET("/map",mapHandler::visualize)
        resources("/**", ClassPathResource("static/"))
    }
}