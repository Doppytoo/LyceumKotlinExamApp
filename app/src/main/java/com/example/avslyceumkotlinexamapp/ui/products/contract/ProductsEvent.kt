package com.example.avslyceumkotlinexamapp.ui.products.contract

sealed class ProductsEvent {
    data object OnGetMoreButtonClicked: ProductsEvent()
}