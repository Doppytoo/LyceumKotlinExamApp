package com.example.avslyceumkotlinexamapp.ui.products.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.avslyceumkotlinexamapp.data.models.ProductModel
import com.example.avslyceumkotlinexamapp.ui.products.contract.ProductsEvent

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductCard(
    product: ProductModel,
    onEvent: (ProductsEvent) -> Unit,
) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)) {

            GlideImage(
                model = product.thumbnail,
                contentDescription = "Product thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.White, shape = RoundedCornerShape(4.dp))
                    .clip(RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("\$${product.price}", style = MaterialTheme.typography.titleLarge)

                RatingIndicator(product.rating)
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
            )
            Text(product.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductCardPreview() {
    val product = ProductModel(
        title = "TEST",
        description = "This is such a beautiful product for testing purposes. Lorem ipsum dolor sit amet...",
        id = 0,
        price = 29.95,
        rating = 4.34f,
        thumbnail = "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/thumbnail.png"
    )

    ProductCard(product, onEvent = {})
}