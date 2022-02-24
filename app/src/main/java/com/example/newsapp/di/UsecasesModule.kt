package com.example.newsapp.di

import com.example.usecases.GetItemUseCase
import com.example.usecases.GetItemUseCaseImpl
import com.example.usecases.GetItemsUseCase
import com.example.usecases.GetItemsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UsecasesModule {
    @Provides
    fun providesGetItemsUseCaseImpl(getItemsUseCaseImpl: GetItemsUseCaseImpl): GetItemsUseCase {
        return getItemsUseCaseImpl
    }

    @Provides
    fun providesGetItemUseCaseImpl(getItemUseCaseImpl: GetItemUseCaseImpl): GetItemUseCase {
        return getItemUseCaseImpl
    }
}