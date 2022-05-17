package com.item.shopping.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.item.shopping.data.FAVORITE_PAGE_SIZE
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

/**
 * Favorite Repo.
 */
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

    /**
     * 페이징용 Method. MVVM 아키텍처에 기반한 안드로이드 의존도를 없애기 위해, flow로 리턴.
     */
    override fun getFavorPagerItems(): Flow<PagingData<Favorite>> {
        return Pager(
            config = PagingConfig(
                pageSize = FAVORITE_PAGE_SIZE,
                initialLoadSize = FAVORITE_PAGE_SIZE,
            ),
            pagingSourceFactory = {
                FavoritePagingSource(
                    favoriteDataSource = favoriteDataSource,
                    defaultDispatcher = defaultDispatcher
                )
            }
        ).flow
    }
}