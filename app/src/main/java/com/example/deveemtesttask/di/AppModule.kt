package com.example.deveemtesttask.di

import android.content.Context
import androidx.room.Room
import com.example.deveemtesttask.data.core.Constants.BASE_URL
import com.example.deveemtesttask.data.local.room.db.AppDatabase
import com.example.deveemtesttask.data.remote.apiService.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApi(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "products").build()
    }

    @Singleton
    @Provides
    fun provideComicDao(db: AppDatabase) = db.productDao()
}