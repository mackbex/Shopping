package com.item.shopping.domain.usecase

import androidx.paging.PagingData
import androidx.paging.filter
import com.item.shopping.domain.model.Goods
import com.item.shopping.domain.repository.FavoriteRepository
import com.item.shopping.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetMainPageItemsUseCase @Inject constructor(
    private val shoppingRepository: ShoppingRepository,
) {

    suspend fun getMainItem() = shoppingRepository.getMainItem()

    fun getGoods(lastId:Int) = shoppingRepository.getGoods(lastId)

}