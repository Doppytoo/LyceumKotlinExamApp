package com.example.avslyceumkotlinexamapp.ui.products.contract

import com.example.avslyceumkotlinexamapp.data.models.ProductModel

sealed interface ProductsEffect {
    data class OpenDetails(val product: ProductModel): ProductsEffect
}