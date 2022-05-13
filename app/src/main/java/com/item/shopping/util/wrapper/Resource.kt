package com.item.shopping.util.wrapper

/**
 * Response 결과 Wrapper
 */
sealed class Resource<out T> {
    object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val msg: String? = null) : Resource<Nothing>()
}


inline fun <reified T: Any, reified R: Any> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Failure -> Resource.Failure(msg)
        is Resource.Loading -> Resource.Loading
    }
}
