package ru.pugovishnikova.example.testvkproject.managers

import retrofit2.Response
import ru.pugovishnikova.example.testvkproject.models.Product
import ru.pugovishnikova.example.testvkproject.models.ProductResponse
import ru.pugovishnikova.example.testvkproject.providers.ItemProductProvider
import ru.pugovishnikova.example.testvkproject.providers.SearchProvider

object SearchManager {
    suspend fun findItem(name: String): ProductResponse {
        return SearchProvider.findItem(name)
    }
}