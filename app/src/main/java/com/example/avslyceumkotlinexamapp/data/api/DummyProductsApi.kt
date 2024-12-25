package com.example.avslyceumkotlinexamapp.data.api

import com.example.avslyceumkotlinexamapp.data.models.ProductModel
import com.example.avslyceumkotlinexamapp.data.models.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyProductsApi {
    @GET("/products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 10,
        @Query("skip") skip: Int = 0, // Default to 0 for the first page
//        @Query("select") select: String = "title,price"
    ): ProductsResponse

//    @GET
//    suspend fun getProductsByPage(
//        page: Int,
//        limit: Int = 10,
//        select: String = "title,price"
//    ): List<ProductModel> {
//        val skip = page * limit
//        return getProducts(limit = limit, skip = skip, select = select)
//    }
}