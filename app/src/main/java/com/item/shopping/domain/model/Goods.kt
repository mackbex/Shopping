package com.item.shopping.domain.model

data class Goods(
    val id:Int,
    val name:String,
    val image:String,
    val isNew:Boolean,
    val sellCount:Int,
    val price:Int,
    val actualPrice:Int,
    val isFavorite:Boolean,
    val discount:Int
)