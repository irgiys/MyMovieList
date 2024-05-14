package com.ourapps.mymovielist.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager

import com.ourapps.mymovielist.api.response.SearchItem
import com.ourapps.mymovielist.databinding.ActivityMainBinding
import com.ourapps.mymovielist.ui.MovieDetailActivity.Companion.EXTRA_MOVIE
import com.ourapps.mymovielist.ui.adapter.MovieListAdapter
import com.ourapps.mymovielist.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    private var lastClick: Long = 0
    private val clickDelay = 500

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text.toString()
                    searchView.hide()
                    mainViewModel.findMovie(searchView.text.toString())
                    false
                }
        }
        mainViewModel.movieList.observe(this) {
            showMovieList(it)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.isError.observe(this) { error ->
            if (error) errorOccurred()
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                pbLoading.visibility = View.VISIBLE
                rvMovie.visibility = View.GONE
            }
        } else {
            binding.apply {
                pbLoading.visibility = View.GONE
                rvMovie.visibility = View.VISIBLE
            }
        }
    }

    private fun errorOccurred() {
        Toast.makeText(this@MainActivity, "An Error Occurred", Toast.LENGTH_SHORT).show()
    }

    private fun showMovieList(movie: ArrayList<SearchItem>) {
        if (movie.size == 0) Toast.makeText(
            this@MainActivity,
            "Movie not Found",
            Toast.LENGTH_SHORT
            
        )
            .show()

        val movieListAdapater = MovieListAdapter(movie)
        binding.rvMovie.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieListAdapater
            setHasFixedSize(true)
        }

        movieListAdapater.setOnItemClickCallback(object : MovieListAdapter.OnItemClickCallback {
            override fun onItemClicked(movie: SearchItem) {
                val clickTime = System.currentTimeMillis()
                if (clickTime - lastClick > clickDelay) {
                    navigateToMovieDetail(movie.imdbID)
                }
            }
        })
    }

    private fun navigateToMovieDetail(imdbID: String?) {
        Intent(this@MainActivity, MovieDetailActivity::class.java).apply {
            putExtra(EXTRA_MOVIE, imdbID)
        }.also {
            startActivity(it)
        }
    }
}