package com.moter.tutorial.network

import com.moter.tutorial.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface INewsServices {
    @GET("top-headlines?country=us&apiKey=8c3ce04e38e54fc1b3664c344bb56a8d")
    suspend fun getNews(@Query("pageSize") pageSize: Int, @Query("page") page: Int): NewsResponse
}