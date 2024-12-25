package com.example.avslyceumkotlinexamapp.ui.products.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avslyceumkotlinexamapp.App
import com.example.avslyceumkotlinexamapp.data.api.DummyProductsApi
import com.example.avslyceumkotlinexamapp.data.dao.ProductsDatabase
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

    private fun getDb() : ProductsDatabase? {
        return App.getDatabase()
    }

    init {
        val db = getDb()

        db?.let {
            val productsDao = db.productsDao()

            val storedProducts = productsDao.getAll()
            if (storedProducts.isEmpty()) return@let

            state.value = state.value.copy(products = storedProducts, currentPage = storedProducts.last().id.div(10) + 1)
        }

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

                        val db = getDb()
                        Log.d("DB AT INSERT", db.toString())
                        db?.let {
                            val productsDao = db.productsDao()

                            productsDao.insertAll(response.products)
                        }
                    } catch (e: Exception) {
                        Log.e("Products ViewModel", "ERROR", e)
                    }
                }
            }
        }
    }
}