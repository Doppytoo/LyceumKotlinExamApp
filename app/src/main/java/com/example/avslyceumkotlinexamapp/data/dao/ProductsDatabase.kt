package com.example.avslyceumkotlinexamapp.data.dao

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.avslyceumkotlinexamapp.data.models.ProductModel

@Database(entities = [ProductModel::class], version = 1)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductDao
}

@Dao
interface ProductDao {
    @Query("SELECT * FROM productmodel")
    fun getAll(): List<ProductModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: ProductModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<ProductModel>)

    @Delete
    fun delete(product: ProductModel)
}