package com.moter.tutorial.view.ui

import android.util.Log
import androidx.paging.PagingSource
import com.moter.tutorial.model.Article
import com.moter.tutorial.model.NewsResponse
import com.moter.tutorial.network.INewsServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class MainRepository(private val service: INewsServices) : PagingSource<Int, Article>() {

    suspend fun getNews(pageSize: Int, page: Int): Flow<NewsResponse> {
        return flow {
            val response = service.getNews(pageSize, page)
            Log.d("MainRepository", "response  ${response.toString()}")
            emit(response)

        }.flowOn(Dispatchers.IO)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = service.getNews(10, currentLoadingPageKey)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                response.articles,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e.fillInStackTrace())
        }
    }
}