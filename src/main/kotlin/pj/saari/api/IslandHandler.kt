import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_PLAIN
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.BodyInserters.fromPublisher
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import pj.saari.Island
import pj.saari.api.getRemoteMap
import pj.saari.map.createMatrix
import pj.saari.map.dfsMatrixForIslands
import pj.saari.map.matrixAsString
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

class IslandHandler(private val reactiveMongoTemplate: ReactiveMongoTemplate) {


    fun findOne(request: ServerRequest): Mono<ServerResponse> = validate
            .request(request)
            {
                val island = reactiveMongoTemplate
                        .findById(request.pathVariable("id").toInt(), Island::class.java)

                island
                        .flatMap {
                            ServerResponse.ok()
                                    .contentType(APPLICATION_JSON)
                                    .body(fromPublisher(island, Island::class.java))
                                    .toMono<ServerResponse>()
                        }
                        .switchIfEmpty(ServerResponse.notFound().build())
            }

    fun findAll(request: ServerRequest): Mono<ServerResponse> = validate
            .request(request)
            {
                val islands = reactiveMongoTemplate
                        .findAll(Island::class.java)
                ServerResponse.ok()
                        .body(fromPublisher(islands, Island::class.java))
                        .switchIfEmpty(ServerResponse.notFound().build())

            }

    fun initialize(request: ServerRequest): Mono<ServerResponse> = validate
            .request(request) {
                val mapResponse = getRemoteMap()
                val islands = dfsMatrixForIslands(createMatrix(mapResponse.attributes.tiles))
                val op1 = reactiveMongoTemplate.dropCollection(Island::class.java)
                val op2 = reactiveMongoTemplate.createCollection(Island::class.java)
                val op3 = reactiveMongoTemplate.insertAll(islands)
                Mono.first(op1)
                        .then(op2)
                        .then(op3.toMono())
                        .flatMap { ServerResponse.noContent().build() }
            }

    fun visualize(request: ServerRequest): Mono<ServerResponse> {
        val mapResponse = getRemoteMap()

        return ServerResponse.ok()
                .contentType(TEXT_PLAIN)
                .body(fromObject(matrixAsString(createMatrix(mapResponse.attributes.tiles))))
    }

}
