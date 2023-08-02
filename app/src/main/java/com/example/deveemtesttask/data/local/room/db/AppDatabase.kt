package com.example.deveemtesttask.data.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deveemtesttask.data.local.room.dao.AppDao
import com.example.deveemtesttask.data.local.room.entity.CategoriesEntity
import com.example.deveemtesttask.data.local.room.entity.ProductsEntity


@Database(entities = [CategoriesEntity::class, ProductsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): AppDao
}