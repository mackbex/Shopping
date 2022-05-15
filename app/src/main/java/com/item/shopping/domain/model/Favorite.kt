package com.item.shopping.domain.model


data class Favorite(
    val id:Int,
    val name:String,
    val image:String,
    val price:Int,
    var discount:Int
)

fun Favorite.mapToGoods():Goods {
    return Goods(
        id = this.id,
        name = this.name,
        image = this.image,
        isNew = false,
        sellCount = 0,
        price = this.price,
        actualPrice = this.price,
        isFavorite = true,
        discount = 0
    )
}