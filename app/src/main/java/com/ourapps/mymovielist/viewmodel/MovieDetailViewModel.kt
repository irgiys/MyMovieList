package com.ourapps.mymovielist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ourapps.mymovielist.api.ApiConfig
import com.ourapps.mymovielist.api.response.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel: ViewModel() {
    private val _isLoading = MutableLiveData(true)
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(true)
    val isError : LiveData<Boolean> = _isError

    private val _movie = MutableLiveData<DetailResponse?>()
    val movie : LiveData<DetailResponse?> = _movie

    fun getMovieDetail(imdbID: String){
        _isLoading.value = true
        val API_KEY = "72cc1d7c"

        ApiConfig.getApiService().detailMovie(API_KEY, imdbID).apply {
            enqueue(object :  Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful) _movie.value = response.body()
                    else Log.e(TAG, response.message())

                    _isLoading.value = false
                    _isError.value = false
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    Log.e(TAG, t.message.toString())

                    _isLoading.value = false
                    _isError.value = true
                }
            })
        }
    }
    companion object {
        private val TAG = MovieDetailViewModel::class.java.simpleName
    }
}