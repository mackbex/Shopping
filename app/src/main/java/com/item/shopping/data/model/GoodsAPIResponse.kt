package com.item.shopping.data.model

import com.google.gson.annotations.SerializedName


data class GoodsAPIResponse(
    @SerializedName("goods")
    val goods:List<GoodsResponse>
)