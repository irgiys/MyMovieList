package com.ourapps.mymovielist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ourapps.mymovielist.api.ApiConfig
import com.ourapps.mymovielist.api.response.SearchItem
import com.ourapps.mymovielist.api.response.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _isNotFound = MutableLiveData(false)
    val isNotFound: LiveData<Boolean> = _isNotFound


    private val _movieList = MutableLiveData<ArrayList<SearchItem>>()
    val movieList: LiveData<ArrayList<SearchItem>> = _movieList

    init {
        findMovie("naruto")
    }

    fun findMovie(query : String){
        _isLoading.value = true
        val API_KEY = "72cc1d7c"
        ApiConfig.getApiService().searchMovie(API_KEY,query).apply {
            enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful && response.body()?.response == "True") {
                        _movieList.value = response.body()?.search
                    }
                    else {
                        _movieList.value = arrayListOf()
                        Log.e(TAG, response.message())
                    }
                    _isLoading.value = false
                    _isError.value = false
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                    _movieList.value = arrayListOf()
                    _isError.value = true
                    _isLoading.value = false
                }

            })
        }
    }
    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

}