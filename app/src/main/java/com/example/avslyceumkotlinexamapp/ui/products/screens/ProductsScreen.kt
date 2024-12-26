package com.example.avslyceumkotlinexamapp.ui.products.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.avslyceumkotlinexamapp.ui.products.components.ProductCard
import com.example.avslyceumkotlinexamapp.ui.products.contract.ProductsEffect
import com.example.avslyceumkotlinexamapp.ui.products.contract.ProductsEvent
import com.example.avslyceumkotlinexamapp.ui.products.contract.ProductsState
import com.example.avslyceumkotlinexamapp.ui.products.screens.destinations.ProductDetailsScreenDestination
import com.example.avslyceumkotlinexamapp.ui.products.viewmodel.ProductsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun ProductsScreen(
    navigator: DestinationsNavigator
) {
    val productsViewModel = viewModel<ProductsViewModel>()
    val state by productsViewModel.state.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Магазин всякой шняги") }) }
    ) { innerPadding ->
        ProductsScreenContent(
            state = state,
            onEvent = { productsViewModel.handleEvent(it) },
            modifier = Modifier.padding(innerPadding)
        )
    }

    LaunchedEffect(productsViewModel.effect) {
        productsViewModel.effect.collectLatest { effect ->
            when (effect) {
                is ProductsEffect.OpenDetails -> {
                    navigator.navigate(ProductDetailsScreenDestination(productWithReviews = effect.productWithReviews))
                }
            }
        }
    }
}

@Composable
fun ProductsScreenContent(
    state: ProductsState, onEvent: (ProductsEvent) -> Unit, modifier: Modifier = Modifier
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp)
    ) {


        state.products.forEach { product ->
            item {
                ProductCard(product, onEvent = onEvent)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        item {
            OutlinedButton(onClick = { onEvent(ProductsEvent.OnLoadMoreButtonClicked) }) { Text("Загрузить ещё") }
        }
    }
}