package com.item.shopping.domain.repository

import androidx.paging.PagingData
import com.item.shopping.domain.model.Goods
import com.item.shopping.domain.model.MainItem
import com.item.shopping.util.wrapper.Resource
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    /**
     * 배너 및 상품 메인아이템 로드 Method
     */
    suspend fun getMainItem(): Resource<MainItem>

    /**
     * 스크롤 시 추가로드를 위한 Paging Method
     */
    fun getGoodsPagerItems(): Flow<PagingData<Goods>>
}