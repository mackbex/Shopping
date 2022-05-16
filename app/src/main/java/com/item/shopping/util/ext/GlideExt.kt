package com.item.shopping.util.ext

import android.graphics.drawable.Animatable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.item.shopping.R
import com.item.shopping.util.getProgressbar


/**
 * Glide Ext
 */
fun ImageView.glidePlane(url:String?, endAction:(() -> Unit)? = null) {

    val holder = getProgressbar(this.context).also {
        it.start()
    }

    Glide.with(this.context)
        .asDrawable()
        .load(url ?: kotlin.run { ColorDrawable(ContextCompat.getColor(this.context, R.color.pale_grey)) })
        .error(ColorDrawable(ContextCompat.getColor(this.context, R.color.pale_grey)))
        .placeholder(holder)
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                holder.stop()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                holder.stop()
                endAction?.invoke()
                if(resource is Animatable) {
                    (resource as Animatable).start()
                }
                return false
            }
        })
        .into(this)
}

