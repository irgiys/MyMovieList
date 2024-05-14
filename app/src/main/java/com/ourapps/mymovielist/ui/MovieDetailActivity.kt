package com.ourapps.mymovielist.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels

import com.bumptech.glide.Glide
import androidx.appcompat.app.AppCompatActivity

import com.ourapps.mymovielist.R
import com.ourapps.mymovielist.api.response.DetailResponse
import com.ourapps.mymovielist.databinding.ActivityMovieDetailBinding
import com.ourapps.mymovielist.viewmodel.MovieDetailViewModel

class MovieDetailActivity : AppCompatActivity(), View.OnClickListener {
    private var imdbID: String? = null
    private lateinit var binding: ActivityMovieDetailBinding
    private val movieDetailViewModel by viewModels<MovieDetailViewModel>()

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        imdbID = intent.extras?.get(EXTRA_MOVIE) as String

        setContentView(binding.root)

        movieDetailViewModel.getMovieDetail(imdbID!!)

        movieDetailViewModel.movie.observe(this) { movie ->
            if (movie != null) {
                parseMovieDetail(movie)
            }

        }
        movieDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }


        binding.btnToImdb.setOnClickListener(this)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                pbLoading.visibility = View.VISIBLE
                movieCard.visibility = View.GONE
                btnToImdb.visibility = View.GONE
            }
        } else {
            binding.apply {
                pbLoading.visibility = View.GONE
                movieCard.visibility = View.VISIBLE
                btnToImdb.visibility = View.VISIBLE
            }
        }
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_to_imdb -> {
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://m.imdb.com/title/${imdbID}")
                }.also {
                    startActivity(it)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun parseMovieDetail(movie: DetailResponse) {
        binding.apply {

            tvTitle.text = movie.title

            if (!isNA(movie.imdbVotes)) {
                tvRating.text = movie.imdbRating
                tvVoting.text = "${movie.imdbVotes} votes"
                scoreCard.visibility = View.VISIBLE
            } else {
                scoreCard.visibility = View.GONE
            }

            if (!isNA(movie.awards)) {
                tvAwards.text = movie.imdbRating
                awardsCard.visibility = View.VISIBLE
            } else {
                awardsCard.visibility = View.GONE
            }

            tvDotType.visibility = if(!isNA(movie.runtime) && !isNA(movie.type)) View.VISIBLE
            else View.GONE

            tvType.setTextAndVisible(movie.type)
            tvAwards.setTextAndVisible(movie.awards)
            tvCountry.setTextAndVisible(movie.country)
            tvGenre.setTextAndVisible(movie.genre)
            tvRating.setTextAndVisible(movie.imdbRating)
            tvRuntime.setTextAndVisible(movie.runtime)
            tvYear.setTextAndVisible(movie.year)

            if (!isNA(movie.plot)) {
                plotDesc.setTextAndVisible(movie.plot)
                plotTitle.visibility = View.VISIBLE
            } else {
                plotTitle.visibility = View.GONE
            }


            Glide.with(root.context)
                .load(movie.poster)
                .placeholder(R.drawable.no_poster)
                .into(poster)
        }
    }

    fun isNA(text: String?): Boolean {
        return text == "N/A"
    }

    fun TextView.setTextAndVisible(text: String?) {
        if (!isNA(text)) {
            this.text = text?.replace("\\n", "")
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }
    }
    @Suppress("DEPRECATION")
    fun onBackPressed(view: View) {
        super.onBackPressed()
    }
}