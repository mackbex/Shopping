package com.item.shopping.util.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * 이미지뷰 바인딩어뎁터
 */
@BindingAdapter("banner_image")
fun bindBannerImage(imageView: ImageView, url:String?) {
    imageView.glidePlane(url)
}


@BindingAdapter("goods_image")
fun bindGoodsImage(imageView:ImageView, url:String?) {
    imageView.glidePlane(url)
}


@BindingAdapter("favorite_image")
fun bindFavoriteImage(imageView:ImageView, url:String?) {
    imageView.glidePlane(url)
}