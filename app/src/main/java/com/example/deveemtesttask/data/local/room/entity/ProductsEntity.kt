package com.example.deveemtesttask.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.deveemtesttask.data.remote.model.ProductDto
import com.example.deveemtesttask.data.remote.model.RatingDto

@Entity(tableName = "products")
data class ProductsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val title: String,
    val count: Int,
    val rate: Double,
)

fun ProductsEntity.toDto() =
    ProductDto(
        category = this.category,
        description = this.description,
        image = this.image,
        price = this.price,
        title = this.title,
        rating = RatingDto(count = this.count, rate = this.rate)
    )

