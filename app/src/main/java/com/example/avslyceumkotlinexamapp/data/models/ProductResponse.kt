package com.example.avslyceumkotlinexamapp.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val rating: Float,
    @SerializedName("thumbnail")
    val imageUrl: String,
    val stock: Int,
    val reviews: List<ReviewModel>
) {
    fun toModel(): ProductModel {
        return ProductModel(
            id = this.id,
            title = this.title,
            description = this.description,
            price = this.price,
            rating = this.rating,
            imageUrl = this.imageUrl,
            stock = this.stock
        )
    }
}
