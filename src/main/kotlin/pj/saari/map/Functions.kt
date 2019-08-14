package pj.saari.map

import com.beust.klaxon.Klaxon
import pj.saari.MapResponse


fun parseMapJson(jsonString: String): MapResponse {
    println("JSON "+jsonString)
    return Klaxon().parse<MapResponse>(jsonString)!!
}

