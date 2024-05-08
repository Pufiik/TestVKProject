package ru.pugovishnikova.example.testvkproject.managers

import ru.pugovishnikova.example.testvkproject.models.ProductResponse
import ru.pugovishnikova.example.testvkproject.providers.ProductProvider

object ProductManager {
    suspend fun getData(skip:Int, limit: Int): ProductResponse{
        return ProductProvider.getData(skip, limit)
    }
}