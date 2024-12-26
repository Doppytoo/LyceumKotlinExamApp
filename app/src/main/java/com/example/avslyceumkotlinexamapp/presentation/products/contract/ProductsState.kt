package com.example.avslyceumkotlinexamapp.presentation.products.contract

import com.example.avslyceumkotlinexamapp.data.models.ProductModel

data class ProductsState (
    val currentPage: Int = 0,
    val products: List<ProductModel> = emptyList()
)