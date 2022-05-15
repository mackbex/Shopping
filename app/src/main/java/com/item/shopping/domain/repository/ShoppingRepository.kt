package com.item.shopping.domain.repository

import androidx.paging.PagingData
import com.item.shopping.domain.model.Goods
import com.item.shopping.domain.model.MainItem
import com.item.shopping.util.wrapper.Resource
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    suspend fun getMainItem(): Resource<MainItem>
    fun getGoods(lastId:Int): Flow<PagingData<Goods>>
}