package com.ourapps.mymovielist.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ourapps.mymovielist.db.api.response.SearchItem
import com.ourapps.mymovielist.databinding.FragmentHomeBinding
import com.ourapps.mymovielist.ui.adapter.MovieListAdapter
import com.ourapps.mymovielist.viewmodel.HomeViewModel

class HomeFragment: Fragment(){

    private var lastClick: Long = 0
    private val clickDelay = 500


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text.toString()
                    searchView.hide()
                    homeViewModel.findMovie(searchView.text.toString())
                    false
                }
        }
        homeViewModel.movieList.observe(this) {
            showMovieList(it)
        }
        homeViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        homeViewModel.isError.observe(this) { error ->
            if (error) errorOccurred()
        }
        // Inflate the layout for this fragment
        return binding.root

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
        Toast.makeText(activity, "An Error Occurred", Toast.LENGTH_SHORT).show()
    }

    private fun showMovieList(movie: ArrayList<SearchItem>) {
        if (movie.size == 0) Toast.makeText(
            activity,
            "Movie not Found",
            Toast.LENGTH_SHORT

        )
            .show()

        val movieListAdapater = MovieListAdapter(movie)
        binding.rvMovie.apply {
            layoutManager = LinearLayoutManager(activity)
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
        Intent(activity, MovieDetailActivity::class.java).apply {
            putExtra(MovieDetailActivity.EXTRA_MOVIE, imdbID)
        }.also {
            startActivity(it)
        }
    }
}