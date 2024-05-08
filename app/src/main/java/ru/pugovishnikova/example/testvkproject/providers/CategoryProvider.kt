package ru.pugovishnikova.example.testvkproject.providers

import ru.pugovishnikova.example.testvkproject.accessors.RetrofitAccessor
import ru.pugovishnikova.example.testvkproject.models.ProductResponse

object CategoryProvider {
    private var allCategories: MutableList<String> = mutableListOf()
    val categories: List<String>
        get() = allCategories.toList()
    suspend fun getCategories(){
        val response = RetrofitAccessor.api().getAllCategories()
        return when (response.isSuccessful) {
            true -> allCategories = response.body()!!.toMutableList()
            else -> throw Exception(response.raw().message())
        }
    }

    suspend fun getProductsByCategory(name: String): ProductResponse {
        val response = RetrofitAccessor.api().getProductsByCategory(name)
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw Exception(response.raw().message())
        }
    }
}