package pj.saari.map

import pj.saari.Island
import pj.saari.Tile
import pj.saari.TileCoord
import pj.saari.TileType


fun createMatrix(tiles: List<Tile>): Array<IntArray> {

    fun getDimensions(tiles: List<Tile>): List<Int> {
        var x = 0
        var y = 0
        tiles.forEach {
            if (it.x > x) x = it.x
            if (it.y > y) y = it.y
        }
        return listOf(x, y)
    }

    val (x, y) = getDimensions(tiles)
    val array = Array(size = y) { IntArray(x) }
    tiles.forEach {
        if (it.type == TileType.land) {
            array[it.y - 1][it.x - 1] = 1
        } else {
            array[it.y - 1][it.x - 1] = 0
        }
    }
    return array
}

fun matrixAsString(matrix: Array<IntArray>): String {
    var rendered = ""
    matrix.forEach { x ->
        x.forEach { y ->
            rendered += y
        }
        rendered += '\n'
    }
    return rendered
}

fun dfsMatrixForIslands(matrix: Array<IntArray>): List<Island> {
    val offsets = intArrayOf(-1, 0, +1)
    fun neighborExists(matrix: Array<IntArray>, i: Int, j: Int): Boolean {
        if ((i >= 0) && (i < matrix.size) && (j >= 0) && (j < matrix[0].size)) {
            return matrix[i][j] == 1
        }
        return false
    }

    fun dfs(matrix: Array<IntArray>, i: Int, j: Int, visited: Array<IntArray>, count: Int) {
        if (visited[i][j] > 0) return
        visited[i][j] = count

        var xOffset: Int
        var yOffset: Int
        for (xi in offsets.indices) {
            xOffset = offsets[xi]
            for (yi in offsets.indices) {
                yOffset = offsets[yi]

                if (xOffset == 0 && yOffset == 0) continue

                if (neighborExists(matrix, i + xOffset, j + yOffset)) {
                    dfs(matrix, i + xOffset, j + yOffset, visited, count)
                }
            }
        }
    }

    fun clusters(matrix: Array<IntArray>): Array<IntArray> {

        val visited = Array(size = matrix.size) { IntArray(matrix[0].size) }

        var count = 0
        for (i in matrix.indices) for (j in matrix[0].indices) {
            if ((matrix[i][j] == 1) && (visited[i][j] == 0)) {
                count += 1
                dfs(matrix, i, j, visited, count)
            }
        }
        return visited
    }

    fun islandsFromClusters(matrix: Array<IntArray>): List<Island> {
        val islandMap = mutableMapOf<Int, Island>()
        for (i in matrix.indices) for (j in matrix[0].indices) {
            if (matrix[i][j] > 0){
                val id = matrix[i][j]
                val tile = TileCoord(j + 1, i + 1)
                if (! islandMap.containsKey(id)) {
                    islandMap[id] = Island(id).apply { tiles.add(tile) }
                } else {
                    islandMap[id]?.tiles?.add(tile)
                }
            }
        }
        return islandMap.values.toList()
    }

    return islandsFromClusters(clusters(matrix))
}
