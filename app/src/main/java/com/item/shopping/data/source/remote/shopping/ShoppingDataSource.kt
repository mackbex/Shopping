package com.item.shopping.data.source.remote.shopping

import com.item.shopping.data.source.remote.service.ShoppingService
import com.item.shopping.util.getResult
import javax.inject.Inject

class ShoppingDataSource @Inject constructor(
    private val shoppingService: ShoppingService
) {
    suspend fun getMainItem() = getResult { shoppingService.getMainItem() }
    suspend fun getGoods(lastId:Int) = getResult { shoppingService.getGoods(lastId) }
}