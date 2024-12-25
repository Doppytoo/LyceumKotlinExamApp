package com.example.avslyceumkotlinexamapp.ui.products.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.avslyceumkotlinexamapp.ui.products.components.ProductCard
import com.example.avslyceumkotlinexamapp.ui.products.contract.ProductsEvent
import com.example.avslyceumkotlinexamapp.ui.products.contract.ProductsState
import com.example.avslyceumkotlinexamapp.ui.products.viewmodel.ProductsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun ProductsScreen(
    navigator: DestinationsNavigator
) {
    val productsViewModel = viewModel<ProductsViewModel>()
    val state by productsViewModel.state.collectAsState()

    ProductsScreenContent(state = state, onEvent = { productsViewModel.handleEvent(it) })
}

@Composable
fun ProductsScreenContent(
    state: ProductsState, onEvent: (ProductsEvent) -> Unit
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {


        state.products.forEach { product ->
            item {
                ProductCard(product, onEvent = onEvent)
            }
        }

        item {
            OutlinedButton(onClick = { onEvent(ProductsEvent.OnGetMoreButtonClicked) }) { Text("Загрузить ещё") }
        }
    }
}