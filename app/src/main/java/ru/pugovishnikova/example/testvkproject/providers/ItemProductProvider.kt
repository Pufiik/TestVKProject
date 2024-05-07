package ru.pugovishnikova.example.testvkproject.providers

import ru.pugovishnikova.example.testvkproject.accessors.RetrofitAccessor
import ru.pugovishnikova.example.testvkproject.models.Product

object ItemProductProvider {
    suspend fun getProductByID(id: Int): Product{
        val response = RetrofitAccessor.api().getProductByID(id)
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw Exception(response.raw().message())
        }
    }
}