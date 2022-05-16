package com.item.shopping.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.item.shopping.data.model.local.FavoriteEntity
import com.item.shopping.data.source.local.dao.FavoriteDao
import com.item.shopping.data.source.local.favorite.FavoriteDataSource
import com.item.shopping.data.source.local.favorite.FavoritePagingSource
import com.item.shopping.di.AppModule
import com.item.shopping.domain.model.Favorite
import com.item.shopping.domain.model.Goods
import com.item.shopping.domain.repository.FavoriteRepository
import com.item.shopping.util.wrapper.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class FavoriteRepositoryImpl  @Inject constructor(
    private val favoriteDataSource: FavoriteDataSource,
    @AppModule.IODispatcher private val defaultDispatcher: CoroutineDispatcher
):FavoriteRepository {

    override suspend fun addFavorite(goods: Goods) = withContext(defaultDispatcher) {
        return@withContext try{
            favoriteDataSource.addFavorite(FavoriteEntity(
                id = goods.id,
                name = goods.name,
                price = goods.price,
                image = goods.image,
                discount = goods.discount,
                regDate = Date()
            ))

            goods.isFavorite = true
            Resource.Success(goods)
        }
        catch (e:Exception) {
            Resource.Failure(e.message)
        }
    }

    override suspend fun removeFavorite(goods:Goods) = withContext(defaultDispatcher) {
        return@withContext try {
            if(favoriteDataSource.removeFavorite(goods.id) > 0) {
                goods.isFavorite = false
                Resource.Success(goods)
            }else {
                Resource.Failure("Failed to remove Favorite.")
            }
        }
        catch (e:Exception) {
            Resource.Failure(e.message)
        }
    }

    override fun getFavorPagerItems(): Flow<PagingData<Favorite>> {
        return Pager(
            config = PagingConfig(
                pageSize = FavoriteDao.FAVORITE_PAGE_SIZE,
                initialLoadSize = FavoriteDao.FAVORITE_PAGE_SIZE,
            ),
            pagingSourceFactory = {
                FavoritePagingSource(
                    favoriteDataSource = favoriteDataSource,
                    defaultDispatcher = defaultDispatcher
                )
            }
        ).flow
    }

    override suspend fun getAllId() = withContext(defaultDispatcher) {
        return@withContext try {
            Resource.Success(favoriteDataSource.getAllId())
        }
        catch (e:Exception) {
            Resource.Failure(e.message)
        }
    }

    override suspend fun getFavoritesById(list: List<Int>) = withContext(defaultDispatcher) {
        return@withContext try {
            val res = if(list.isNotEmpty()) {
                favoriteDataSource.getFavoritesById(list)
            }
            else {
                favoriteDataSource.getAllId()
            }
            Resource.Success(res)
        }
        catch (e:Exception) {
            Resource.Failure(e.message)
        }
    }

}