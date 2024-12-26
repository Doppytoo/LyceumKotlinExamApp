package com.example.avslyceumkotlinexamapp.presentation.products.contract

import com.example.avslyceumkotlinexamapp.data.models.ProductWithReviews

sealed interface ProductsEffect {
    data class OpenDetails(val productWithReviews: ProductWithReviews): ProductsEffect
}