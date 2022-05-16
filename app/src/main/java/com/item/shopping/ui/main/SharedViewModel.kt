package com.item.shopping.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.item.shopping.domain.model.Goods
import com.item.shopping.domain.usecase.ManageFavoriteUseCase
import com.item.shopping.util.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 좋아요 업데이트용 viewmodel. HomeFragment와 연동이 되어야 하기 때문에, SharedViewModel에서 구현.
 */
@HiltViewModel
class SharedViewModel @Inject constructor(
    private val manageFavoriteUseCase: ManageFavoriteUseCase
):ViewModel() {

    private var _updateFavoriteLiveData = MutableLiveData<Resource<Goods>>().apply { value = Resource.Loading }
    val updateFavoriteLiveData:LiveData<Resource<Goods>> = _updateFavoriteLiveData

    fun updateFavorite(goods: Goods) {
        viewModelScope.launch {
            _updateFavoriteLiveData.postValue(manageFavoriteUseCase.updateFavorite(goods))

        }
    }
}