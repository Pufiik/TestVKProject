package ru.pugovishnikova.example.testvkproject.managers

import ru.pugovishnikova.example.testvkproject.models.ProductResponse
import ru.pugovishnikova.example.testvkproject.providers.CategoryProvider

object CategoryManager {

    suspend fun getProductsByCategories(name: String): ProductResponse{
        return CategoryProvider.getProductsByCategory(name)
    }
}