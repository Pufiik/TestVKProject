package ru.pugovishnikova.example.testvkproject.managers

import ru.pugovishnikova.example.testvkproject.models.ProductResponse
import ru.pugovishnikova.example.testvkproject.providers.SearchProvider

object SearchManager {
    suspend fun findItem(name: String): ProductResponse {
        return SearchProvider.findItem(name)
    }
}