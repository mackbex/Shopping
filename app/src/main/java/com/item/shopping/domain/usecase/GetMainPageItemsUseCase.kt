package com.item.shopping.domain.usecase

import com.item.shopping.domain.repository.ShoppingRepository
import javax.inject.Inject


class GetMainPageItemsUseCase @Inject constructor(
    private val shoppingRepository: ShoppingRepository,
) {

    suspend fun getMainItem() = shoppingRepository.getMainItem()

    fun getGoods() = shoppingRepository.getGoods()

}