package com.example.avslyceumkotlinexamapp.presentation.products.contract

import com.example.avslyceumkotlinexamapp.data.models.ProductWithReviews

data class ProductsState (
    val currentPage: Int = 0,
    val productsWithReviews: List<ProductWithReviews> = emptyList()
)