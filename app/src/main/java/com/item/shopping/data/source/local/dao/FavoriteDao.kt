package com.item.shopping.data.source.local.dao

import androidx.room.*
import com.item.shopping.data.model.local.FavoriteEntity

/**
 * Favorite Local Room Dao
 */
@Dao
interface FavoriteDao {

    @Query("SELECT id FROM favorite")
    suspend fun getAllId():List<Int>

    @Query("select * from favorite order by created_date DESC LIMIT :loadSize OFFSET :index * :loadSize")
    suspend fun getFavoriteItems(index : Int, loadSize : Int) : List<FavoriteEntity>

    @Query("SELECT id FROM favorite WHERE id IN (:ids)")
    suspend fun getFavoriteByIds(ids: IntArray): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int):Int
}