package com.item.shopping

import com.item.shopping.data.source.remote.shopping.ShoppingDataSource
import com.item.shopping.di.NetworkModule
import com.item.shopping.util.wrapper.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DataSourceTest {
    private val okHttpClient by lazy { NetworkModule.provideOkHttpClient() }
    private val retrofit by lazy { NetworkModule.provideShoppingRetrofit(okHttpClient) }
    private val service by lazy { NetworkModule.provideShoppingService(retrofit) }
    private val shoppingDataSource = ShoppingDataSource(service)


    @Test
    fun `test goods service`() = runTest {
        var goods = shoppingDataSource.getMainItem()
        Assert.assertTrue(goods is Resource.Success<*>)
    }


}