package com.item.shopping.domain.usecase

import com.item.shopping.domain.model.Goods
import com.item.shopping.domain.repository.FavoriteRepository
import com.item.shopping.util.wrapper.Resource
import javax.inject.Inject

/**
 * Favorite 화면 UseCase
 */
class ManageFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    /**
     * 상품 좋아요 상태 업로드
     */
    suspend fun updateFavorite(goods: Goods):Resource<Goods> {
        return if(goods.isFavorite) {
            favoriteRepository.removeFavorite(goods)
        }
        else {
            favoriteRepository.addFavorite(goods)
        }
    }

    /**
     * 좋아요 표시한 상품 아이템들을 Local에서 가져옴.
     */
    fun getFavoriteList() = favoriteRepository.getFavorPagerItems()

}