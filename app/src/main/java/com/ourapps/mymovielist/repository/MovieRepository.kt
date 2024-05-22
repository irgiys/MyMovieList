package com.ourapps.mymovielist.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ourapps.mymovielist.db.entity.MovieEntity
import com.ourapps.mymovielist.db.room.MovieDao
import com.ourapps.mymovielist.db.room.MovieDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MovieRepository(application : Application) {
    private val movieDao : MovieDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = MovieDatabase.getDatabase(application)
        movieDao = db.movieDao()
    }
    fun getAllMovie() : LiveData<List<MovieEntity>> = movieDao.getAllMovies()

    fun getByImdbID(imdbID: String?) : LiveData<List<MovieEntity>> = movieDao.getByImdbID(imdbID)


    fun insert(movieEntity : MovieEntity) = executorService.execute{ movieDao.insert(movieEntity)}

    fun update(movieEntity : MovieEntity) = executorService.execute{ movieDao.update(movieEntity)}

    fun delete(movieEntity: MovieEntity) = executorService.execute{movieDao.delete(movieEntity)}

}