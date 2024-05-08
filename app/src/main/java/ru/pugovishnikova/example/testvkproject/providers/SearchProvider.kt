package ru.pugovishnikova.example.testvkproject.providers

import retrofit2.Response
import ru.pugovishnikova.example.testvkproject.accessors.RetrofitAccessor
import ru.pugovishnikova.example.testvkproject.models.Product
import ru.pugovishnikova.example.testvkproject.models.ProductResponse

object SearchProvider {
    suspend fun findItem(name: String): ProductResponse {
        val response = RetrofitAccessor.api().search(name)
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw Exception(response.raw().message())
        }
    }
}