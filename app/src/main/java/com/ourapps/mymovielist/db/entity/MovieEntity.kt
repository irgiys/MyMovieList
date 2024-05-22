package com.ourapps.mymovielist.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie")
@Parcelize
data class MovieEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "imdbID")
    val imdbID: String,

    @ColumnInfo(name = "poster")
    val posterUrl: String? = null,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "year")
    val year: String? = null,

    @ColumnInfo(name = "type")
    val type: String? = null,
) : Parcelable