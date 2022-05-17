package com.item.shopping.ui.main.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.item.shopping.domain.usecase.ManageFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val manageFavoriteUseCase: ManageFavoriteUseCase
):ViewModel(){

    /**
     * Favorite 리스트 collect
     */
    fun getFavorites() = manageFavoriteUseCase.getFavoriteList().cachedIn(viewModelScope)

}