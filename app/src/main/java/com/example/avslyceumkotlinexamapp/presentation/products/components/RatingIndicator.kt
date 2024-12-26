package com.example.avslyceumkotlinexamapp.presentation.products.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RatingIndicator(
    rating: Float,
    totalStars: Int = 5,
    filledStarColor: Color = Color(0xffffcc00),
    emptyStarColor: Color = Color.Gray
) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.height(24.dp)
    ) {
        val filledStars = rating.toInt() // Fully filled stars
        val fractionalPart = rating - filledStars // Fractional star part

        for (i in 1..totalStars) {
            when {
                i <= filledStars -> {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Filled Star",
                        tint = filledStarColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                i == filledStars + 1 && fractionalPart > 0 -> {
                    FractionalStar(
                        painter = rememberVectorPainter(Icons.Filled.Star),
                        fraction = fractionalPart,
                        color = filledStarColor,
                        backgroundColor = emptyStarColor,
                        size = 24.0f,
                    )
                }

                else -> {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Empty Star",
                        tint = emptyStarColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Text(
            "${if (rating == rating.toInt().toFloat()) rating.toInt() else rating}/5",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray
            ),
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
fun FractionalStar(
    painter: Painter,
    fraction: Float,
    color: Color,
    backgroundColor: Color,
    size: Float
) {
    Canvas(modifier = Modifier.size(size.dp)) {
        // Draw the background (empty star)
        with(painter) {
            draw(
                size = this@Canvas.size,
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(backgroundColor)
            )
        }

        // Clip and draw the filled part of the star
        clipRect(
            left = 0f,
            right = this.size.width * fraction,
            top = 0f,
            bottom = this.size.height
        ) {
            with(painter) {
                draw(
                    size = this@Canvas.size,
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(color)
                )
            }
        }
    }
}