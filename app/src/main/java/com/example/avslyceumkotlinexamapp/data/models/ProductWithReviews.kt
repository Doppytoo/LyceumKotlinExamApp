package com.example.avslyceumkotlinexamapp.data.models

import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.serialization.Serializable

@Serializable
data class ProductWithReviews(
    @Embedded val product: ProductModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    val reviews: List<ReviewModel>
)
