package com.item.shopping.data.model.remote

import com.google.gson.annotations.SerializedName
import com.item.shopping.domain.model.Banner
import com.item.shopping.domain.model.Goods
import com.item.shopping.domain.model.MainItem


/**
 * remote DataSource 모델
 */

data class MainItemAPIResponse(
    @SerializedName("banners")
    val banner:List<BannerResponse>,
    @SerializedName("goods")
    val goods:List<GoodsResponse>
)


fun MainItemAPIResponse.mapToDomain() = MainItem(
    banners = this.banner.map { it.mapToDomain() },
    goods = this.goods.map { it.mapToDomain() }
)

data class BannerResponse(
    @SerializedName("id")
    val id:Int,
    @SerializedName("image")
    val image:String
)

fun BannerResponse.mapToDomain() = Banner(
    id = this.id,
    image = this.image
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

fun GoodsResponse.mapToDomain() = Goods(
    id = this.id,
    name = this.name,
    image = this.image,
    isNew = this.isNew,
    sellCount = this.sellCount,
    price = this.price,
    actualPrice = this.actualPrice,
    isFavorite = false,
    discount = if(this.actualPrice > 0) ((this.actualPrice - this.price) / this.actualPrice.toFloat() * 100).toInt() else 0
)