package com.example.avslyceumkotlinexamapp.ui.products.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.avslyceumkotlinexamapp.data.models.ReviewModel


@Composable
fun ReviewCard(
    review: ReviewModel
) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text("${review.reviewerName} (${review.reviewerEmail})", style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(4.dp))
            RatingIndicator(review.rating)
            Spacer(Modifier.height(4.dp))

            Text(review.comment)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReviewCardPreview() {
    val review = ReviewModel(
        reviewerName = "John Doe",
        reviewerEmail = "john.doe@example.com",
        rating = 4f,
        comment = "Not bad at all, not bad I say...",
        productId = 0
    )

    ReviewCard(review)
}