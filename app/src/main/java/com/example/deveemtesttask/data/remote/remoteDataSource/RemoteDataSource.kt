package com.example.deveemtesttask.data.remote.remoteDataSource

import com.example.deveemtesttask.data.core.bases.BaseDataSource
import com.example.deveemtesttask.data.remote.apiService.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) : BaseDataSource() {

    suspend fun fetchCategories() = getResult { apiService.fetchCategories() }

    suspend fun fetchProducts(categoryName: String) = getResult { apiService.fetchProducts(categoryName) }
}