package com.example.deveemtesttask.data.remote.apiService

import com.example.deveemtesttask.data.remote.model.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("categories")
    suspend fun fetchCategories(
    ): Response<List<String>>

    @GET("category/{categoryName}")
    suspend fun fetchProducts(
        @Path("categoryName") categoryName: String,
    ): Response<List<ProductDto>>
}

