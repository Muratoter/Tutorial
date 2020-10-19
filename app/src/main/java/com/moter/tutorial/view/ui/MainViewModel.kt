package com.moter.tutorial.view.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.moter.tutorial.model.NewsResponse
import com.moter.tutorial.network.INewsServices
import com.moter.tutorial.network.NewsService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val _newsResponse = MutableLiveData<NewsResponse>()
    val newsResponse: LiveData<NewsResponse> get() = _newsResponse

    init {
        viewModelScope.launch {
            mainRepository.getNews(10, 1)
                .onStart {
                    Log.d("MainViewModel", "onStart")
                }
                .catch { exception ->
                    Log.d("MainViewModel", "exception ${exception}")
                }
                .collect {
                    Log.d("MainViewModel", "response ${it.toString()}")
                    _newsResponse.value = it
                }
        }

        val listData = Pager(PagingConfig(pageSize = 10)) {
            mainRepository
        }.flow.cachedIn(viewModelScope)

    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = _newsResponse.value
            if (immutableQuery != null) {
                Log.d("MainViewModel", "request More")
//                viewModelScope.launch {
//                    repository.requestMore(immutableQuery)
//                }
            }
        }
    }
}