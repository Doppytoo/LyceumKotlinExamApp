package com.example.avslyceumkotlinexamapp.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    foreignKeys = [ForeignKey(
        entity = ProductModel::class,
        parentColumns = ["id"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ReviewModel(
    @PrimaryKey(autoGenerate = true) val reviewId: Int = 0,
    val productId: Int, // Foreign key
    val rating: Float,
    val comment: String,
    val reviewerName: String,
    val reviewerEmail: String
)


/*
{
      "rating": 2,
      "comment": "Very unhappy with my purchase!",
      "date": "2024-05-23T08:56:21.618Z",
      "reviewerName": "John Doe",
      "reviewerEmail": "john.doe@x.dummyjson.com"
    },
 */