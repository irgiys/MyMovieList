package com.ourapps.mymovielist.db.api

import com.ourapps.mymovielist.db.api.response.DetailResponse
import com.ourapps.mymovielist.db.api.response.SearchResponse
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
        @Query("i") imdbID : String
    ): Call<DetailResponse>


}