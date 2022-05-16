package com.item.shopping.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.item.shopping.data.model.remote.mapToDomain
import com.item.shopping.data.source.local.favorite.FavoriteDataSource
import com.item.shopping.data.source.remote.service.ShoppingService.Companion.GOODS_PAGE_SIZE
import com.item.shopping.data.source.remote.shopping.GoodsPagingSource
import com.item.shopping.data.source.remote.shopping.ShoppingDataSource
import com.item.shopping.di.AppModule
import com.item.shopping.domain.model.Goods
import com.item.shopping.domain.repository.ShoppingRepository
import com.item.shopping.util.wrapper.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    private val shoppingDataSource: ShoppingDataSource,
    private val favoriteDataSource: FavoriteDataSource,

    @AppModule.IODispatcher private val defaultDispatcher: CoroutineDispatcher
): ShoppingRepository {

    override suspend fun getMainItem() = withContext(defaultDispatcher) {
        val res = shoppingDataSource.getMainItem()
        return@withContext res.map { it.mapToDomain() }
    }

    override fun getGoods(): Flow<PagingData<Goods>> {
         return Pager(
            config = PagingConfig(
                pageSize = GOODS_PAGE_SIZE,
                initialLoadSize = GOODS_PAGE_SIZE
            ),
            pagingSourceFactory = {
                GoodsPagingSource(
                    shoppingDataSource = shoppingDataSource,
                    favoriteDataSource = favoriteDataSource,
                    defaultDispatcher = defaultDispatcher
                )
            }
        ).flow
    }
}