package com.example.avslyceumkotlinexamapp.presentation.products.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.avslyceumkotlinexamapp.data.models.ProductWithReviews
import com.example.avslyceumkotlinexamapp.presentation.products.components.RatingIndicator
import com.example.avslyceumkotlinexamapp.presentation.products.components.ReviewCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun ProductDetailsScreen(
    navigator: DestinationsNavigator,
    productWithReviews: ProductWithReviews
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        content = { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back button") },
                        onClick = { navigator.navigateUp() })
                },
                title = { Text(productWithReviews.product.title) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp)
        ) {

            item {
                GlideImage(
                    model = productWithReviews.product.imageUrl,
                    contentDescription = "Product thumbnail",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.padding(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "\$${productWithReviews.product.price}",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text("В наличии ${productWithReviews.product.stock}шт.")
//                RatingIndicator(product.rating)
                }

                Spacer(
                    modifier = Modifier.height(4.dp)
                )
                Text(
                    productWithReviews.product.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    RatingIndicator(productWithReviews.product.rating)
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                    )
                }
            }

            if (productWithReviews.reviews.isEmpty()) {
                item {
                    Text(
                        "Отзывы не найдены\n(На товар нет отзывов или отсутствует подключение к интернету)",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                productWithReviews.reviews.forEach { review ->
                    item {
                        Spacer(Modifier.height(8.dp))
                        ReviewCard(review)
                    }
                }
            }
        }
    }
}
