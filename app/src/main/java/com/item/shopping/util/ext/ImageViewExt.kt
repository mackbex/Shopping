package com.item.shopping.util.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter


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