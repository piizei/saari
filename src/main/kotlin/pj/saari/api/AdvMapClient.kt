package pj.saari.api

import khttp.get
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import pj.saari.MapResponse
import pj.saari.map.parseMapJson


var client = WebClient
        .builder()
        .baseUrl("https://pjtesting.s3-eu-west-1.amazonaws.com/mock.json")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

fun getRemoteMap(): MapResponse {
    val r = get("https://pjtesting.s3-eu-west-1.amazonaws.com/mock.json")
    return parseMapJson(r.text)
}
