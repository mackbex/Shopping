package com.item.shopping.data.source.remote.shopping

import androidx.paging.PagingSource
import androidx.paging.PagingState
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


    private var lastId = 0

    override fun getRefreshKey(state: PagingState<Int, Goods>): Int? {
        return state.anchorPosition?.let { position ->

//            (position / SHOPPING_PAGE_SIZE) * SHOPPING_PAGE_SIZE
            state.closestPageToPosition(position)?.prevKey?.plus(GOODS_PAGE_SIZE)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(GOODS_PAGE_SIZE)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Goods> = withContext(defaultDispatcher) {
        var page = params.key ?: 0


        return@withContext try {

            val goods = if(lastId == 0) {
                shoppingDataSource.getMainItem().map { it.goods }
            }
            else {
                shoppingDataSource.getGoods(page).map { it.goods }
            }

            when(goods) {
                is Resource.Success -> {

                    var nextKey:Int? = null

                    val data:List<Goods> = if(goods.data.isNotEmpty()) {

                        //paging Data로 변환 및 할인 데이터 계산
                        val data = goods.data.map {
                            it.mapToDomain().apply {
                                discount = ((it.actualPrice - it.price) / it.actualPrice.toFloat() * 100).toInt()
                            }
                        }

                        //Favorite 확인
                        val favoriteList = favoriteDataSource.getFavoritesById(data.map { it.id })

                        data.map {
                            it.isFavorite = favoriteList.contains(it.id)
                        }
                        lastId = data.last().id
                        nextKey = lastId
                        data
                    }
                    else {
                        listOf()
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
}