package com.item.shopping.domain.usecase

import com.item.shopping.domain.repository.ShoppingRepository
import javax.inject.Inject


class ShoppingUseCase @Inject constructor(
    private val shoppingRepository: ShoppingRepository,
) {
    suspend fun getMainItem() = shoppingRepository.getMainItem()
    fun getLoLMatches(listId:Int) = shoppingRepository.getGoods(listId)
}