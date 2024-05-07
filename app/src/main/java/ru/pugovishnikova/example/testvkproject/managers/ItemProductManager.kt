package ru.pugovishnikova.example.testvkproject.managers

import ru.pugovishnikova.example.testvkproject.models.Product
import ru.pugovishnikova.example.testvkproject.providers.ItemProductProvider

object ItemProductManager {
    suspend fun getData(id: Int): Product {
        return ItemProductProvider.getProductByID(id)
    }
}