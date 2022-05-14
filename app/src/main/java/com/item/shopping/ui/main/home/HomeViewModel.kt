package com.item.shopping.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.item.shopping.domain.model.Banner
import com.item.shopping.domain.model.Goods
import com.item.shopping.domain.model.MainItem
import com.item.shopping.util.SingleLiveEvent
import com.item.shopping.util.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private var _bannerLiveData = SingleLiveEvent<Resource<MainItem>>()
    val bannerLiveData:LiveData<Resource<MainItem>> = _bannerLiveData

    init {
        getMainItems()
    }

    fun getMainItems() {

        viewModelScope.launch {
            _bannerLiveData.postValue(
                Resource.Success(
                    MainItem(
                        banners = listOf(
                            Banner(
                                id = 0,
                                image = "https://img.a-bly.com/banner/images/banner_image_1615465448476691.jpg"
                            ),
                            Banner(
                                id = 1,
                                image = "https://img.a-bly.com/banner/images/banner_image_1615962899391279.jpg"
                            ),
                            Banner(
                                id = 2,
                                image = "https://img.a-bly.com/banner/images/banner_image_1615970086333899.jpg"
                            )
                        ),
                        goods = listOf(
                            Goods(
                                id = 0,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210225_1614264987060340s.gif",
                                isNew = false,
                                sellCount = 1024,
                                price = 10000,
                                actualPrice = 12000,
                                isFavorite = false,
                                discount = 20
                            ),
                            Goods(
                                id = 1,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210305_1614930254984142s.gif",
                                isNew = true,
                                sellCount = 101524,
                                price = 23,
                                actualPrice = 42,
                                isFavorite = false,
                                discount = 1
                            ),Goods(
                                id = 0,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210225_1614264987060340s.gif",
                                isNew = false,
                                sellCount = 1024,
                                price = 10000,
                                actualPrice = 12000,
                                isFavorite = false,
                                discount = 20
                            ),
                            Goods(
                                id = 1,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210305_1614930254984142s.gif",
                                isNew = true,
                                sellCount = 101524,
                                price = 23,
                                actualPrice = 42,
                                isFavorite = false,
                                discount = 1
                            ),Goods(
                                id = 0,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210225_1614264987060340s.gif",
                                isNew = false,
                                sellCount = 1024,
                                price = 10000,
                                actualPrice = 12000,
                                isFavorite = false,
                                discount = 20
                            ),
                            Goods(
                                id = 1,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210305_1614930254984142s.gif",
                                isNew = true,
                                sellCount = 101524,
                                price = 23,
                                actualPrice = 42,
                                isFavorite = false,
                                discount = 1
                            ),Goods(
                                id = 0,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210225_1614264987060340s.gif",
                                isNew = false,
                                sellCount = 1024,
                                price = 10000,
                                actualPrice = 12000,
                                isFavorite = false,
                                discount = 20
                            ),
                            Goods(
                                id = 1,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210305_1614930254984142s.gif",
                                isNew = true,
                                sellCount = 101524,
                                price = 23,
                                actualPrice = 42,
                                isFavorite = false,
                                discount = 1
                            ),Goods(
                                id = 0,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210225_1614264987060340s.gif",
                                isNew = false,
                                sellCount = 1024,
                                price = 10000,
                                actualPrice = 12000,
                                isFavorite = false,
                                discount = 20
                            ),
                            Goods(
                                id = 1,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210305_1614930254984142s.gif",
                                isNew = true,
                                sellCount = 101524,
                                price = 23,
                                actualPrice = 42,
                                isFavorite = false,
                                discount = 1
                            ),Goods(
                                id = 0,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210225_1614264987060340s.gif",
                                isNew = false,
                                sellCount = 1024,
                                price = 10000,
                                actualPrice = 12000,
                                isFavorite = false,
                                discount = 20
                            ),
                            Goods(
                                id = 1,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210305_1614930254984142s.gif",
                                isNew = true,
                                sellCount = 101524,
                                price = 23,
                                actualPrice = 42,
                                isFavorite = false,
                                discount = 1
                            ),Goods(
                                id = 0,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210225_1614264987060340s.gif",
                                isNew = false,
                                sellCount = 1024,
                                price = 10000,
                                actualPrice = 12000,
                                isFavorite = false,
                                discount = 20
                            ),
                            Goods(
                                id = 1,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210305_1614930254984142s.gif",
                                isNew = true,
                                sellCount = 101524,
                                price = 23,
                                actualPrice = 42,
                                isFavorite = false,
                                discount = 1
                            ),Goods(
                                id = 0,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210225_1614264987060340s.gif",
                                isNew = false,
                                sellCount = 1024,
                                price = 10000,
                                actualPrice = 12000,
                                isFavorite = false,
                                discount = 20
                            ),
                            Goods(
                                id = 1,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210305_1614930254984142s.gif",
                                isNew = true,
                                sellCount = 101524,
                                price = 23,
                                actualPrice = 42,
                                isFavorite = false,
                                discount = 1
                            ),Goods(
                                id = 0,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210225_1614264987060340s.gif",
                                isNew = false,
                                sellCount = 1024,
                                price = 10000,
                                actualPrice = 12000,
                                isFavorite = false,
                                discount = 20
                            ),
                            Goods(
                                id = 1,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210305_1614930254984142s.gif",
                                isNew = true,
                                sellCount = 101524,
                                price = 23,
                                actualPrice = 42,
                                isFavorite = false,
                                discount = 1
                            ),Goods(
                                id = 0,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210225_1614264987060340s.gif",
                                isNew = false,
                                sellCount = 1024,
                                price = 10000,
                                actualPrice = 12000,
                                isFavorite = false,
                                discount = 20
                            ),
                            Goods(
                                id = 1,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210305_1614930254984142s.gif",
                                isNew = true,
                                sellCount = 101524,
                                price = 23,
                                actualPrice = 42,
                                isFavorite = false,
                                discount = 1
                            ),Goods(
                                id = 0,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210225_1614264987060340s.gif",
                                isNew = false,
                                sellCount = 1024,
                                price = 10000,
                                actualPrice = 12000,
                                isFavorite = false,
                                discount = 20
                            ),
                            Goods(
                                id = 1,
                                name = "TEST",
                                image = "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210305_1614930254984142s.gif",
                                isNew = true,
                                sellCount = 101524,
                                price = 23,
                                actualPrice = 42,
                                isFavorite = false,
                                discount = 1
                            ),
                        )
                    )
                )
            )
        }

    }


}