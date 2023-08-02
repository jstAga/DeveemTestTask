package com.example.deveemtesttask.data.remote.model

import com.example.deveemtesttask.data.local.room.entity.ProductsEntity

data class ProductDto(
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val rating: RatingDto,
    val title: String,
) : java.io.Serializable

data class RatingDto(
    val count: Int,
    val rate: Double,
)

fun ProductDto.toEntity() = ProductsEntity(
    category = this.category,
    description = this.description,
    image = this.image,
    price = this.price,
    title = this.title,
    rate = this.rating.rate,
    count = this.rating.count,
    id = 0
)
