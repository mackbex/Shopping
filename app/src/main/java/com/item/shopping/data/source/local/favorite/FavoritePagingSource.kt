package com.item.shopping.data.source.local.favorite

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.item.shopping.data.model.local.mapToFavorite
import com.item.shopping.domain.model.Favorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Favorite 페이저용 데이터소스
 */
class FavoritePagingSource(
    private val favoriteDataSource: FavoriteDataSource,
    private val defaultDispatcher: CoroutineDispatcher
): PagingSource<Int, Favorite>() {
    override fun getRefreshKey(state: PagingState<Int, Favorite>): Int? {
        return state.anchorPosition?.let { achorPosition ->
            state.closestPageToPosition(
                achorPosition
            )?.prevKey?.plus(1) ?: state.closestPageToPosition(achorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>) = withContext(defaultDispatcher) {
        val position = params.key ?: 0
        return@withContext try {
            val loadData = favoriteDataSource.getFavoriteItems(position, params.loadSize).map {
                it.mapToFavorite()
            }

            LoadResult.Page(
                data = loadData,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (loadData.isNullOrEmpty()) null else position + 1
            )
        }
        catch (e:Exception) {
            LoadResult.Error(e)
        }
    }
}