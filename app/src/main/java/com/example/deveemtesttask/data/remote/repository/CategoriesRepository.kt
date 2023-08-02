package com.example.deveemtesttask.data.remote.repository

import com.example.deveemtesttask.data.core.Resource
import com.example.deveemtesttask.data.core.bases.BaseRepository
import com.example.deveemtesttask.data.local.room.dao.AppDao
import com.example.deveemtesttask.data.local.room.entity.CategoriesEntity
import com.example.deveemtesttask.data.local.room.entity.toList
import com.example.deveemtesttask.data.remote.remoteDataSource.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CategoriesRepository @Inject constructor(
    private val appDao: AppDao,
    private val remoteDataSource: RemoteDataSource,
) : BaseRepository() {

    fun isCategoriesSaved() = doLocalRequest { appDao.isCategoriesSaved() > 0 }

    fun createCategory(category: String) =
        doLocalRequest { appDao.createCategory(CategoriesEntity(category = category)) }

    fun getCategories(): Flow<Resource<List<String>>> {
        val isSaved = runBlocking { checkIsSaved() }
        return if (isSaved) {
            doLocalRequest { appDao.readAllCategories().toList() }
        } else {
            doRemoteRequest { remoteDataSource.fetchCategories() }
        }
    }

    private suspend fun checkIsSaved(): Boolean {
        return appDao.isCategoriesSaved() > 0
    }
}

