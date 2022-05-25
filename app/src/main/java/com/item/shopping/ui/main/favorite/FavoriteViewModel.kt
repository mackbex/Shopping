package com.item.shopping.ui.main.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.item.shopping.domain.model.Favorite
import com.item.shopping.domain.usecase.ManageFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val manageFavoriteUseCase: ManageFavoriteUseCase
):ViewModel(){

    val favoriteState = manageFavoriteUseCase.getFavoriteList().cachedIn(viewModelScope).stateIn(
        initialValue = PagingData.empty(),
        started = SharingStarted.WhileSubscribed(0),
        scope = viewModelScope
    )

}