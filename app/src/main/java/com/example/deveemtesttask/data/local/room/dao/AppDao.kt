package com.example.deveemtesttask.data.local.room.dao

import androidx.room.*
import com.example.deveemtesttask.data.local.room.entity.CategoriesEntity
import com.example.deveemtesttask.data.local.room.entity.ProductsEntity

@Dao
interface AppDao {

    @Insert
    suspend fun createProduct(productsEntity: ProductsEntity)

    @Query("SELECT * FROM products WHERE category = :category")
    suspend fun readProductsByCategory(category: String): List<ProductsEntity>

    @Update
    suspend fun updateProduct(productsEntity: ProductsEntity)

    @Delete
    suspend fun deleteProduct(productsEntity: ProductsEntity)

    @Query("SELECT COUNT(*) FROM products WHERE category = :category")
    suspend fun isProductsByCategorySaved(category: String): Int

    @Query("SELECT * FROM products WHERE category = :category ORDER BY price ASC")
    suspend fun readProductsSortedByAsc(category: String): List<ProductsEntity>

    @Query("SELECT * FROM products WHERE category = :category ORDER BY price DESC")
    suspend fun readProductsSortedByDesc(category: String): List<ProductsEntity>


    @Insert
    suspend fun createCategory(categoriesEntity: CategoriesEntity)

    @Query("SELECT * FROM categories")
    suspend fun readAllCategories(): List<CategoriesEntity>

    @Update
    suspend fun updateCategory(categoriesEntity: CategoriesEntity)

    @Delete
    suspend fun deleteCategory(categoriesEntity: CategoriesEntity)

    @Query("SELECT COUNT(*) FROM categories")
    suspend fun isCategoriesSaved(): Int
}