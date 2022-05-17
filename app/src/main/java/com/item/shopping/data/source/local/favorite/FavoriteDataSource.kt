package com.item.shopping.data.source.local.favorite

import com.item.shopping.data.model.local.FavoriteEntity
import com.item.shopping.data.source.local.dao.FavoriteDao
import javax.inject.Inject

/**
 * Favorite DataSource
 */

class FavoriteDataSource @Inject constructor(
    private val favoriteDao: FavoriteDao,
){
    suspend fun addFavorite(favorite: FavoriteEntity) {
        favoriteDao.addFavorite(favorite)
    }

    suspend fun removeFavorite(id:Int):Int {
        return favoriteDao.deleteFavoriteById(id)
    }

    suspend fun getFavoriteItems(index:Int, loadSize:Int):List<FavoriteEntity> {
        return favoriteDao.getFavoriteItems(index, loadSize)
    }

    suspend fun getFavoritesById(list:List<Int>):List<Int> {
        return favoriteDao.getFavoriteByIds(list.toIntArray())
    }
}
