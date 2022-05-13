package com.item.shopping.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class IODispatcher


    @IODispatcher
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}