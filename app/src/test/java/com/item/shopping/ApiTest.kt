package com.item.shopping

import com.item.shopping.data.source.remote.shopping.ShoppingDataSource
import com.item.shopping.di.NetworkModule
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class ApiTest {
    private val okHttpClient by lazy { NetworkModule.provideOkHttpClient() }
    private val retrofit by lazy { NetworkModule.provideShoppingRetrofit(okHttpClient) }
    private val service by lazy { NetworkModule.provideShoppingService(retrofit) }
    private val shoppingDataSource = ShoppingDataSource(service)



}