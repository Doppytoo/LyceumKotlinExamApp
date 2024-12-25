package com.example.avslyceumkotlinexamapp

import android.app.Application
import androidx.room.Room
import com.example.avslyceumkotlinexamapp.data.dao.ProductsDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            ProductsDatabase::class.java,
            "products-db"
        )
            .allowMainThreadQueries()
            .build()
    }

    companion object {
        var db: ProductsDatabase? = null
        fun getDatabase(): ProductsDatabase? {
            return db
        }
    }
}