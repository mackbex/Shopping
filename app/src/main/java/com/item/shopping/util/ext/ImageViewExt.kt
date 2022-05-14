package com.item.shopping.util.ext

import android.R.attr.path
import android.graphics.ImageDecoder
import android.graphics.Movie
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import java.io.ByteArrayOutputStream
import java.net.URL


@BindingAdapter("banner_image")
fun bindBannerImage(imageView: ImageView, url:String?) {
    imageView.glidePlane(url)
}


@BindingAdapter("goods_image")
fun bindGoodsImage(imageView:ImageView, url:String?) {
    imageView.glidePlane(url)
}