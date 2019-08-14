package pj.saari

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class Error(val code: Int, val messages: List<String>)
data class TileList(val tiles: List<Tile>)
data class Tile(val x: Int, val y: Int, val type: TileType)
data class TileCoord(val x: Int, val y: Int)
data class MapResponse(val data: Any, var attributes: TileList)

@Document
data class Island(@Id val id: Int) {
    val tiles: MutableSet<TileCoord> = mutableSetOf()
}

enum class TileType { land, water }

