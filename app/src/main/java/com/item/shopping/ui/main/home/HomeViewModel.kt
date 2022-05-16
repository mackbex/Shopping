package com.item.shopping.ui.main.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.item.shopping.domain.model.Goods
import com.item.shopping.domain.model.MainItem
import com.item.shopping.domain.usecase.GetMainPageItemsUseCase
import com.item.shopping.domain.usecase.ManageFavoriteUseCase
import com.item.shopping.util.SingleLiveEvent
import com.item.shopping.util.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMainPageListsUseCase: GetMainPageItemsUseCase,
    private val manageFavoriteUseCase: ManageFavoriteUseCase
) : ViewModel() {

    private var _bannerLiveData = MutableLiveData<Resource<MainItem>>().apply { value = Resource.Loading }
    val bannerLiveData:LiveData<Resource<MainItem>> = _bannerLiveData
    private var _updateFavoriteLiveData = SingleLiveEvent<Resource<Goods>>().apply { value = Resource.Loading }
    val updateFavoriteLiveData:LiveData<Resource<Goods>> = _updateFavoriteLiveData

    init {
        getMainItems()
    }

    fun getMainItems() {
        viewModelScope.launch {
            _bannerLiveData.postValue(Resource.Loading)
            _bannerLiveData.postValue(getMainPageListsUseCase.getMainItem())
        }
    }

    fun getGoods(lastId:Int = 0) = getMainPageListsUseCase.getGoods(lastId).cachedIn(viewModelScope)


    fun updateFavorite(goods: Goods) {
      viewModelScope.launch {
          _updateFavoriteLiveData.postValue(manageFavoriteUseCase.updateFavorite(goods))
      }
    }
}