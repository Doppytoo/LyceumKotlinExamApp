package com.example.avslyceumkotlinexamapp.data.api

import com.example.avslyceumkotlinexamapp.data.models.ProductResponse
import com.example.avslyceumkotlinexamapp.data.models.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DummyProductsApi {
    @GET("/products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 10,
        @Query("skip") skip: Int = 0,
    ): ProductsResponse

    @GET("/products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): ProductResponse
}