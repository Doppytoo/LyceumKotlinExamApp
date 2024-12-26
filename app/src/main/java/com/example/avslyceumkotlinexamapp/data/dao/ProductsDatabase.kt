package com.example.avslyceumkotlinexamapp.data.dao

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Transaction
import com.example.avslyceumkotlinexamapp.data.models.ProductModel
import com.example.avslyceumkotlinexamapp.data.models.ReviewModel
import com.example.avslyceumkotlinexamapp.data.models.ProductWithReviews

@Database(entities = [ProductModel::class, ReviewModel::class], version = 1)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProducts(product: List<ProductModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllReviews(reviews: List<ReviewModel>)

    @Query("SELECT * FROM ProductModel")
    fun getAllProducts(): List<ProductModel>

    @Transaction
    @Query("SELECT * FROM ProductModel WHERE id = :productId")
    fun getProductWithReviews(productId: Int): ProductWithReviews

    @Transaction
    fun insertProductWithReviews(product: ProductModel, reviews: List<ReviewModel>) {
        insertProduct(product)
        insertAllReviews(reviews)
    }

    @Transaction
    fun insertAllProductsWithReviews(products: List<ProductModel>, reviews: List<ReviewModel>) {
        insertAllProducts(products)
        insertAllReviews(reviews)
    }
}
