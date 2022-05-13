package com.item.shopping.util

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.item.shopping.util.wrapper.Resource
import retrofit2.Response

fun getProgressbar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
    }
}


suspend inline fun <T> getResult(crossinline call: suspend () -> Response<T>): Resource<T> {
    try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return Resource.Success(body)
        }
        return error(" ${response.code()} ${response.message()}")
    } catch (e: Exception) {
        return error(e.message ?: e.toString())
    }
}

fun <T> error(message: String): Resource<T> {
    return Resource.Failure("Network call has failed for a following reason: $message")
}