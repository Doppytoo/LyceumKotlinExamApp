package com.example.avslyceumkotlinexamapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponse (
    val products: List<ProductResponse>
)