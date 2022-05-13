package com.item.shopping.data.repository

import android.content.Context
import com.item.shopping.data.source.remote.shopping.ShoppingDataSource
import com.item.shopping.di.AppModule
import com.item.shopping.domain.model.Goods
import com.item.shopping.domain.repository.ShoppingRepository
import com.item.shopping.util.wrapper.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val shoppingDataSource: ShoppingDataSource,
    @AppModule.IODispatcher private val defaultDispatcher: CoroutineDispatcher
): ShoppingRepository {
    override suspend fun getMainItem() = withContext(defaultDispatcher) {

        return@withContext Resource.Failure()
    }

    override fun getGoods(lastId:Int):Resource<List<Goods>>  {

        return Resource.Failure()
    }

}