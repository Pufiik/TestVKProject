package ru.pugovishnikova.example.testvkproject.accessors

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.pugovishnikova.example.testvkproject.models.Product
import ru.pugovishnikova.example.testvkproject.models.ProductResponse

interface ApiAccessor {

    @GET("/products/{id}")
    suspend fun getProductByID(@Path(value = "id") id: Int): Response<Product>

    @GET("/products/search")
    suspend fun search(@Query("q") q: String): Response<ProductResponse>



    @GET("/products")
    suspend fun getLimitProducts(@Query("skip") skip: Int, @Query("limit") limit: Int): Response<ProductResponse>
}