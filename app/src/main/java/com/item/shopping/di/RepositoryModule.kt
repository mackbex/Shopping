package com.item.shopping.di

import com.item.shopping.data.repository.ShoppingRepositoryImpl
import com.item.shopping.domain.repository.ShoppingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindShoppingRepository(impl: ShoppingRepositoryImpl): ShoppingRepository

}