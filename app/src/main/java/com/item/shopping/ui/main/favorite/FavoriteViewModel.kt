package com.item.shopping.ui.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.item.shopping.domain.model.Favorite
import com.item.shopping.domain.model.Goods
import com.item.shopping.domain.model.mapToGoods
import com.item.shopping.domain.usecase.ManageFavoriteUseCase
import com.item.shopping.util.SingleLiveEvent
import com.item.shopping.util.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val manageFavoriteUseCase: ManageFavoriteUseCase
):ViewModel(){

    private var _updateFavoriteLiveData = SingleLiveEvent<Resource<Goods>>().apply { value = Resource.Loading }
    val updateFavoriteLiveData:LiveData<Resource<Goods>> = _updateFavoriteLiveData


    fun getFavorites() = manageFavoriteUseCase.getFavoriteList().cachedIn(viewModelScope)

    fun updateFavorite(favorite: Favorite) {
        viewModelScope.launch {
            _updateFavoriteLiveData.postValue(manageFavoriteUseCase.updateFavorite(favorite.mapToGoods()))
        }
    }
}