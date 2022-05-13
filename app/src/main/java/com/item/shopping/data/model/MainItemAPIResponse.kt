package com.item.shopping.data.model

import com.google.gson.annotations.SerializedName

data class MainItemAPIResponse(
    @SerializedName("banners")
    val banner:List<BannerResponse>,
    @SerializedName("goods")
    val goods:List<GoodsResponse>
)

data class BannerResponse(
    @SerializedName("id")
    val id:Int,
    @SerializedName("image")
    val image:String
)

data class GoodsResponse(
    @SerializedName("id")
    val id:Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("image")
    val image:String,
    @SerializedName("is_new")
    val isNew:Boolean,
    @SerializedName("sell_count")
    val sellCount:Int,
    @SerializedName("actual_price")
    val actualPrice:Int,
    @SerializedName("price")
    val price:Int,
)