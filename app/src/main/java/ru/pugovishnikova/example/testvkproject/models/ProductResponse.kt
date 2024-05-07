package ru.pugovishnikova.example.testvkproject.models

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products")
    val data: List<Product>
)
