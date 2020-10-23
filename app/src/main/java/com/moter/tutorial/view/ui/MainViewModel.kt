package com.moter.tutorial.view.ui

import androidx.lifecycle.*
import androidx.paging.*


class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val listData = Pager(PagingConfig(pageSize = 10)) {
        mainRepository
    }.flow.cachedIn(viewModelScope)


}