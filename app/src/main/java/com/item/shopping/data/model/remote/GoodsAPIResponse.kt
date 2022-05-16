package com.item.shopping.data.model.remote

import com.google.gson.annotations.SerializedName
import com.item.shopping.domain.model.Goods


data class GoodsAPIResponse(
    @SerializedName("goods")
    val goods:List<GoodsResponse>
)

fun GoodsAPIResponse.mapToDomain():List<Goods> {
    return this.goods.map {
        it.mapToDomain()
    }
}