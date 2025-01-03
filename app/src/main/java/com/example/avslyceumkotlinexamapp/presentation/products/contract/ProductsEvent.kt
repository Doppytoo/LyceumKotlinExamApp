package com.example.avslyceumkotlinexamapp.presentation.products.contract

import com.example.avslyceumkotlinexamapp.data.models.ProductModel

sealed class ProductsEvent {
    data class OnCardClicked(val product: ProductModel): ProductsEvent()

    data object OnLoadMoreButtonClicked: ProductsEvent()
}