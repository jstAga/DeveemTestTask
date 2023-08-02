package com.example.deveemtesttask.data.remote.repository

import com.example.deveemtesttask.data.core.Resource
import com.example.deveemtesttask.data.core.bases.BaseRepository
import com.example.deveemtesttask.data.local.room.dao.AppDao
import com.example.deveemtesttask.data.local.room.entity.toDto
import com.example.deveemtesttask.data.remote.model.ProductDto
import com.example.deveemtesttask.data.remote.model.toEntity
import com.example.deveemtesttask.data.remote.remoteDataSource.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val appDao: AppDao,
    private val remoteDataSource: RemoteDataSource,
) : BaseRepository() {

    fun isProductsByCategorySaved(category: String) =
        doLocalRequest { appDao.isProductsByCategorySaved(category) > 0 }

    fun getProducts(category: String): Flow<Resource<List<ProductDto>>> {
        val isSaved = runBlocking { checkIsSaved(category) }
        return if (isSaved) {
            doLocalRequest { appDao.readProductsByCategory(category).map { it.toDto() } }
        } else {
            doRemoteRequest { remoteDataSource.fetchProducts(category) }
        }
    }

    private suspend fun checkIsSaved(category: String): Boolean {
        return appDao.isProductsByCategorySaved(category) > 0
    }


    fun createProduct(productDto: ProductDto) =
        doLocalRequest { appDao.createProduct(productDto.toEntity()) }

    fun getProductAsc(category: String): Flow<Resource<List<ProductDto>>> {
        return doLocalRequest { appDao.readProductsSortedByAsc(category).map { it.toDto() } }
    }

    fun getProductDesc(category: String): Flow<Resource<List<ProductDto>>> {
        return doLocalRequest { appDao.readProductsSortedByDesc(category).map { it.toDto() } }
    }
}