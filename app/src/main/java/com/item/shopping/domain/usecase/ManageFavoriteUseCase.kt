package com.item.shopping.domain.usecase

import com.item.shopping.domain.model.Goods
import com.item.shopping.domain.repository.FavoriteRepository
import com.item.shopping.util.wrapper.Resource
import javax.inject.Inject

class ManageFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    suspend fun updateFavorite(goods: Goods):Resource<Goods> {
        return if(goods.isFavorite) {
            favoriteRepository.removeFavorite(goods)
        }
        else {
            favoriteRepository.addFavorite(goods)
        }
    }

    fun getFavoriteList() = favoriteRepository.getFavorPagerItems()

}