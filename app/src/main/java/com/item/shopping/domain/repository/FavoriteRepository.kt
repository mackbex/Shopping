package com.item.shopping.domain.repository

import androidx.paging.PagingData
import com.item.shopping.domain.model.Favorite
import com.item.shopping.domain.model.Goods
import com.item.shopping.util.wrapper.Resource
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    /**
     * Favorite Local Room에 추가.
     * 상품 이미지 좋아요 버튼 누를 시 실행.
     */
    suspend fun addFavorite(goods: Goods):Resource<Goods>
    /**
     * Favorite Local Room에서 삭제.
     * 상품 이미지 좋아요 버튼 누를 시 실행.
     */
    suspend fun removeFavorite(goods: Goods):Resource<Goods>
    /**
     * Favorite 화면에 뿌려질 Paging3용 아이템 로드 Method.
     */
    fun getFavorPagerItems(): Flow<PagingData<Favorite>>

    /**
     * 전체 favorite id 가져오는 Method
     */
    suspend fun getAllId():Resource<List<Int>>

}

