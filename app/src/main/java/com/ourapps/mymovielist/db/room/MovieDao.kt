package com.ourapps.mymovielist.db.room

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ourapps.mymovielist.db.entity.MovieEntity


interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movieEntity: MovieEntity)

    @Update
    fun update(movieEntity: MovieEntity)

    @Delete
    fun delete(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie ORDER BY id ASC")
    fun getAllMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * from movie WHERE imdbID = :imdbID")
    fun getByImdbID(imdbID: String?): LiveData<List<MovieEntity>>
}