package com.item.shopping.domain.repository

import androidx.paging.PagingData
import com.item.shopping.domain.model.Favorite
import com.item.shopping.domain.model.Goods
import com.item.shopping.util.wrapper.Resource
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun addFavorite(goods: Goods):Resource<Goods>
    suspend fun removeFavorite(goods: Goods):Resource<Goods>
    fun getFavorPagerItems(): Flow<PagingData<Favorite>>
    suspend fun getAllId():Resource<List<Int>>
    suspend fun getFavoritesById(list:List<Int>):Resource<List<Int>>
}

