package com.item.shopping.data.source.remote.service

import com.item.shopping.data.model.remote.GoodsAPIResponse
import com.item.shopping.data.model.remote.MainItemAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Retrofit 서비스
 */
interface ShoppingService {

    companion object {
        const val BASE_URL_V1 = "http://d2bab9i9pr8lds.cloudfront.net/api/"
        const val GOODS_PAGE_SIZE=10
    }

    @GET("home")
    suspend fun getMainItem(
    ) : Response<MainItemAPIResponse>

    @GET("home/goods")
    suspend fun getGoods(
        @Query("lastId") createDate: Int
    ) : Response<GoodsAPIResponse>

}