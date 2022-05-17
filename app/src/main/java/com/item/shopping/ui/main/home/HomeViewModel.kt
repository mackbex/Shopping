package com.item.shopping.ui.main.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.item.shopping.domain.model.MainItem
import com.item.shopping.domain.usecase.GetMainPageItemsUseCase
import com.item.shopping.util.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMainPageListsUseCase: GetMainPageItemsUseCase,
) : ViewModel() {

    private var _bannerLiveData = MutableLiveData<Resource<MainItem>>().apply { value = Resource.Loading }
    val bannerLiveData:LiveData<Resource<MainItem>> = _bannerLiveData

    init {
        getMainItems()
    }

    /**
     * 메인 아이템 호출
     */
    fun getMainItems() {
        viewModelScope.launch {
            _bannerLiveData.postValue(Resource.Loading)
            _bannerLiveData.postValue(getMainPageListsUseCase.getMainItem())
        }
    }
    /**
     * Goods 리스트 collect
     */
    fun getGoodsPagerItems() = getMainPageListsUseCase.getGoodsPagerItems().cachedIn(viewModelScope)

}