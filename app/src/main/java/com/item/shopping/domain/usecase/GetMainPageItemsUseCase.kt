package com.item.shopping.domain.usecase

import com.item.shopping.domain.repository.ShoppingRepository
import javax.inject.Inject

/**
 * 메인화면 UseCase
 */
class GetMainPageItemsUseCase @Inject constructor(
    private val shoppingRepository: ShoppingRepository,
) {

    /**
     * 메인화면 아이템을 호출한다.
     */
    suspend fun getMainItem() = shoppingRepository.getMainItem()

    /**
     * 상품들을 호출한다.
     */
    fun getGoods() = shoppingRepository.getGoods()

}