package com.example.avslyceumkotlinexamapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ReviewModel(
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