package com.example.avslyceumkotlinexamapp.presentation.products.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avslyceumkotlinexamapp.App
import com.example.avslyceumkotlinexamapp.data.api.DummyProductsApi
import com.example.avslyceumkotlinexamapp.data.dao.ProductDao
import com.example.avslyceumkotlinexamapp.data.models.ProductModel
import com.example.avslyceumkotlinexamapp.data.models.ProductWithReviews
import com.example.avslyceumkotlinexamapp.presentation.products.contract.ProductsEffect
import com.example.avslyceumkotlinexamapp.presentation.products.contract.ProductsEvent
import com.example.avslyceumkotlinexamapp.presentation.products.contract.ProductsState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsViewModel : ViewModel() {

    val state = MutableStateFlow(ProductsState())

    private val _effect = Channel<ProductsEffect>()
    val effect = _effect.receiveAsFlow()

    private fun getApi(): DummyProductsApi {
        val okHttpClient = Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()

        return retrofit.create(DummyProductsApi::class.java)
    }

    private fun getDao(): ProductDao? {
        return App.getDatabase()?.productDao()
    }

    init {
        val dao = getDao()

        dao?.let {
            val storedProducts = dao.getAllProductsWithReviews()
            if (storedProducts.isEmpty()) return@let

            state.value = state.value.copy(
                productsWithReviews = storedProducts,
                currentPage = storedProducts.last().product.id.div(10) + 1
            )
        }

        if (state.value.productsWithReviews.isEmpty()) {
            handleEvent(ProductsEvent.OnLoadMoreButtonClicked)
        }
    }

    fun handleEvent(event: ProductsEvent) {
        when (event) {
            ProductsEvent.OnLoadMoreButtonClicked -> {
                val api = getApi()
                viewModelScope.launch {
                    try {
                        val newProducts = api.getProducts(
                            10,
                            10 * (state.value.currentPage + 1)
                        ).products.map { ProductWithReviews(it.toModel(), it.reviews) }
                        state.value = state.value.copy(
                            currentPage = state.value.currentPage + 1,
                            productsWithReviews = state.value.productsWithReviews + newProducts
                        )

                        val dao = getDao()
                        dao?.let {
                            dao.insertAllProductsWithReviewsO(newProducts)
                        }
                    } catch (e: Exception) {
                        Log.e("Products ViewModel", "ERROR", e)
                    }
                }
            }

            is ProductsEvent.OnCardClicked -> {
                viewModelScope.launch {
                    val productWithReviews = state.value.productsWithReviews.find { it.product.id == event.product.id }

                    if (productWithReviews != null) {
                        _effect.send(ProductsEffect.OpenDetails(productWithReviews))
                    } else {
                        Log.e("ProductsViewModel", "Unable to find product with reviews")
                    }
                }
            }
        }
    }
}