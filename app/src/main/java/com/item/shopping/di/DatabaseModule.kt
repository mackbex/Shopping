package com.item.shopping.di

import android.content.Context
import com.item.shopping.data.source.local.AppDatabase
import com.item.shopping.data.source.local.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Room database 모듈
 */
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideFavoriteDao(appDatabase: AppDatabase):FavoriteDao {
        return appDatabase.favoriteDao()
    }
}