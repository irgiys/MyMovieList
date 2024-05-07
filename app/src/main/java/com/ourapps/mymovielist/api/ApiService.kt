package com.ourapps.mymovielist.api

import com.ourapps.mymovielist.api.response.DetailResponse
import com.ourapps.mymovielist.api.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    fun searchMovie(
        @Query("apikey") apiKey : String,
        @Query("s") searchQuery: String
    ): Call<SearchResponse>

    @GET("/")
    fun detailMovie(
        @Query("apikey") apiKey : String,
        @Query("imdbID") imdbID : String
    ): Call<DetailResponse>


}