package com.item.shopping.data.source.remote.shopping

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.item.shopping.data.model.remote.GoodsResponse
import com.item.shopping.data.model.remote.mapToDomain
import com.item.shopping.data.source.local.favorite.FavoriteDataSource
import com.item.shopping.data.source.remote.service.ShoppingService.Companion.GOODS_PAGE_SIZE
import com.item.shopping.domain.model.Goods
import com.item.shopping.util.wrapper.Resource
import com.item.shopping.util.wrapper.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException

/**
 * 페이저용 데이터소스
 */
class GoodsPagingSource constructor(
    private val shoppingDataSource: ShoppingDataSource,
    private val favoriteDataSource: FavoriteDataSource,
    private val defaultDispatcher: CoroutineDispatcher
): PagingSource<Int, Goods>() {


    override fun getRefreshKey(state: PagingState<Int, Goods>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(GOODS_PAGE_SIZE)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(GOODS_PAGE_SIZE)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Goods> = withContext(defaultDispatcher) {
        val page = params.key ?: 0
        return@withContext try {
            val goods = if(page == 0) {
                shoppingDataSource.getMainItem().map { it.goods }
            }
            else {
                shoppingDataSource.getGoods(page).map { it.goods }
            }

            when(goods) {
                is Resource.Success -> {

                    val data = checkFavorites(getPagingData(goods.data))
                    val nextKey = if(data.isEmpty()) {
                        null
                    } else {
                        data.last().id
                    }

                    LoadResult.Page(
                        data = data,
                        prevKey = null,//if(page - SHOPPING_PAGE_SIZE < 0) null else page - SHOPPING_PAGE_SIZE,
                        nextKey = nextKey
                    )
                }
                else -> {
                    LoadResult.Error(IllegalStateException())
                }
            }
        } catch (e: Exception) {
            return@withContext LoadResult.Error(e)
        }
    }

    //paging Data로 변환
    fun getPagingData(list:List<GoodsResponse>):List<Goods> {
        return list.map { it.mapToDomain() }
    }

    //Favorite 확인
    private suspend fun checkFavorites(list:List<Goods>):List<Goods> {
        if(list.isEmpty()) return list
        val favoriteList = favoriteDataSource.getFavoritesById(list.map { it.id })
        return list.apply{
            map {
                it.isFavorite = favoriteList.contains(it.id)
            }
        }
    }
}