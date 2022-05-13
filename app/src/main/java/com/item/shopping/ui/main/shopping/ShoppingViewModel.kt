package com.item.shopping.ui.main.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.item.shopping.domain.model.Banner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(

):ViewModel() {

    val bannerLiveData = MutableLiveData<List<Banner>>()

    init{
        getMainItems()
    }

    fun getMainItems() {

        viewModelScope.launch {
            bannerLiveData.value = listOf(
                Banner(
                    id = 0,
                    image = "https://img.a-bly.com/banner/images/banner_image_1615465448476691.jpg"
                ),
                Banner(
                    id = 1,
                    image = "https://img.a-bly.com/banner/images/banner_image_1615962899391279.jpg"
                ),
                )
        }

    }


}