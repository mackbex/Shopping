package com.item.shopping.domain.model

/**
 * 메인아이템 모델
 */
data class MainItem(
    val banners : List<Banner>,
    val goods : List<Goods>
)