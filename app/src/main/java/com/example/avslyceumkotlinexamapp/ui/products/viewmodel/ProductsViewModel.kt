package com.example.avslyceumkotlinexamapp.ui.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avslyceumkotlinexamapp.data.api.DummyProductsApi
import com.example.avslyceumkotlinexamapp.ui.products.contract.ProductsEvent
import com.example.avslyceumkotlinexamapp.ui.products.contract.ProductsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsViewModel : ViewModel() {

    val state = MutableStateFlow(ProductsState())

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

    init {
        if (state.value.products.isEmpty()) {
            handleEvent(ProductsEvent.OnGetMoreButtonClicked)
        }
    }

    fun handleEvent(event: ProductsEvent) {
        when (event) {
            ProductsEvent.OnGetMoreButtonClicked -> {
                val api = getApi()
                viewModelScope.launch {
                    try {
                        val response = api.getProducts(10, 10 * (state.value.currentPage + 1))
                        state.value = state.value.copy(
                            currentPage = state.value.currentPage + 1,
                            products = state.value.products + response.products
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}