package com.item.shopping.domain.repository

import com.item.shopping.domain.model.Goods
import com.item.shopping.domain.model.MainItem
import com.item.shopping.util.wrapper.Resource

interface ShoppingRepository {
    suspend fun getMainItem(): Resource<MainItem>
    fun getGoods(lastId:Int): Resource<List<Goods>>
}