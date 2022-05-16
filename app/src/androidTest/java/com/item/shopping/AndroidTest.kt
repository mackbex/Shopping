package com.item.shopping

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.item.shopping.data.model.remote.mapToDomain
import com.item.shopping.data.repository.FavoriteRepositoryImpl
import com.item.shopping.data.source.local.AppDatabase
import com.item.shopping.data.source.local.favorite.FavoriteDataSource
import com.item.shopping.data.source.remote.service.ShoppingService.Companion.GOODS_PAGE_SIZE
import com.item.shopping.data.source.remote.shopping.GoodsPagingSource
import com.item.shopping.data.source.remote.shopping.ShoppingDataSource
import com.item.shopping.di.NetworkModule
import com.item.shopping.domain.repository.FavoriteRepository
import com.item.shopping.util.wrapper.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AndroidTest {
    private val okHttpClient by lazy { NetworkModule.provideOkHttpClient() }
    private val retrofit by lazy { NetworkModule.provideShoppingRetrofit(okHttpClient) }
    private val service by lazy { NetworkModule.provideShoppingService(retrofit) }
    private val shoppingDataSource = ShoppingDataSource(service)
    private lateinit var favoriteDataSource:FavoriteDataSource
    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var database: AppDatabase
    @Before
    fun initDB() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.item.shopping", appContext.packageName)

        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
        val favoriteDao = database.favoriteDao()
        favoriteDataSource = FavoriteDataSource(favoriteDao)
        favoriteRepository = FavoriteRepositoryImpl(favoriteDataSource, UnconfinedTestDispatcher())
    }

    /**
     * 페이징 테스트
     */
    @Test
    fun test_goods_paging() = runTest {

        val goodsPagingSource = GoodsPagingSource(shoppingDataSource, favoriteDataSource, UnconfinedTestDispatcher())

        var key = 0
        while(true) {
            //페이징 데이터 로드
            val actual = goodsPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = if(key == 0) null else key,
                    loadSize = GOODS_PAGE_SIZE,
                    placeholdersEnabled = false
                )
            )

            // 페이징 키가 0일때는 메인아이템 리스트 가져옴
            val goodsList = if(key == 0)  {
                val res = shoppingDataSource.getMainItem()
                Assert.assertTrue(res is Resource.Success<*>)
                (res as Resource.Success).data.goods
            }
            else {
                val res = shoppingDataSource.getGoods(key)
                Assert.assertTrue(res is Resource.Success<*>)
                (res as Resource.Success).data.goods
            }
            // 더이상 아이템이 없을때 return.
            if(goodsList.isEmpty()) return@runTest

            val expected = PagingSource.LoadResult.Page(
                data = goodsList.map { it.mapToDomain() },
                prevKey = null,
                nextKey = key + GOODS_PAGE_SIZE
            )

            Assert.assertEquals(
                actual,
                expected
            )

            key += GOODS_PAGE_SIZE
        }

    }

    /**
     * 좋아요 테스트
     */
    @Test
    fun test_favorite_update() = runTest {

        val goods = shoppingDataSource.getMainItem()
        Assert.assertTrue(goods is Resource.Success<*>)

        val goodsList = (goods as Resource.Success).data.goods.map { it.mapToDomain() }

        //10번정도 favorite가 add or remove가 잘되는지 테스트
        for(i in 0 until 10) {
            val goodsItem = goodsList[Random().nextInt(goodsList.size)]

            with(goodsItem) {
                if (isFavorite) {
                    favoriteRepository.removeFavorite(this)
                    val favoriteIds = favoriteRepository.getAllId()
                    Assert.assertTrue(favoriteIds is Resource.Success<*>)
                    val favoriteIdList = (favoriteIds as Resource.Success).data
                    Assert.assertTrue(!favoriteIdList.contains(id))
                } else {
                    favoriteRepository.addFavorite(this)
                    val favoriteIds = favoriteRepository.getAllId()
                    Assert.assertTrue(favoriteIds is Resource.Success<*>)
                    val favoriteIdList = (favoriteIds as Resource.Success).data
                    Assert.assertTrue(favoriteIdList.contains(id))
                }
            }
        }
    }
}