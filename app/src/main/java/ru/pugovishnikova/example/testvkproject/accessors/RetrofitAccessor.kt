package ru.pugovishnikova.example.testvkproject.accessors

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAccessor {

    private val retrofit = Retrofit
        .Builder()
        .baseUrl("https://dummyjson.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service by lazy {
        retrofit.create(ApiAccessor::class.java)
    }

    fun api(): ApiAccessor = service
}