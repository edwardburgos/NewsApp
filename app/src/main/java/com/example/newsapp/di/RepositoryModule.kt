package com.example.newsapp.di

import com.example.data.repository.ItemRepository
import com.example.data.repository.ItemRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun providesItemRepository(itemRepositoryImpl: ItemRepositoryImpl): ItemRepository {
        return itemRepositoryImpl
    }
}