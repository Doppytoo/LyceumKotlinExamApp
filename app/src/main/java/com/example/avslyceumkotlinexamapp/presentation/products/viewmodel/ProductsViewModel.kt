package com.example.avslyceumkotlinexamapp.presentation.products.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avslyceumkotlinexamapp.App
import com.example.avslyceumkotlinexamapp.data.api.DummyProductsApi
import com.example.avslyceumkotlinexamapp.data.dao.ProductDao
import com.example.avslyceumkotlinexamapp.data.models.ProductModel
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
            val storedProducts = dao.getAllProducts()
            if (storedProducts.isEmpty()) return@let

            state.value = state.value.copy(
                products = storedProducts,
                currentPage = storedProducts.last().id.div(10) + 1
            )
        }

        if (state.value.products.isEmpty()) {
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
                        ).products.map { it.toModel() }
                        state.value = state.value.copy(
                            currentPage = state.value.currentPage + 1,
                            products = state.value.products + newProducts
                        )

                        val dao = getDao()
                        dao?.let {
                            dao.insertAllProducts(newProducts)
                        }
                    } catch (e: Exception) {
                        Log.e("Products ViewModel", "ERROR", e)
                    }
                }
            }

            is ProductsEvent.OnCardClicked -> {


                viewModelScope.launch {
                    fetchAndSaveProductWithReviews(productId = event.product.id)

                    val dao = getDao()

                    val productWithReviews = dao!!.getProductWithReviews(event.product.id)

                    _effect.send(ProductsEffect.OpenDetails(productWithReviews))

                }
            }
        }
    }

    private suspend fun fetchAndSaveProductWithReviews(productId: Int) {
        try {
            val api = getApi()
            val dao = getDao()
            val response = api.getProductById(productId)

            val product = ProductModel(
                id = response.id,
                title = response.title,
                description = response.description,
                price = response.price,
                rating = response.rating,
                imageUrl = response.imageUrl,
                stock = response.stock
            )

            val reviews = response.reviews.map { review ->
                review.copy(productId = response.id)
            }

            dao?.insertProductWithReviews(product, reviews)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}