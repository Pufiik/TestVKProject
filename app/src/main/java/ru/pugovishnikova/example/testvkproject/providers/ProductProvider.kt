package ru.pugovishnikova.example.testvkproject.providers

import ru.pugovishnikova.example.testvkproject.accessors.RetrofitAccessor
import ru.pugovishnikova.example.testvkproject.models.ProductResponse

object ProductProvider {
    suspend fun getData(skip: Int, limit: Int): ProductResponse {
        val response = RetrofitAccessor.api().getLimitProducts(skip, limit)
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw Exception(response.raw().message())
        }
    }
}