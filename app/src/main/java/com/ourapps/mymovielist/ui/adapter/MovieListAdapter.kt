package com.ourapps.mymovielist.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ourapps.mymovielist.api.response.SearchItem
import com.ourapps.mymovielist.databinding.MovieItemBinding
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.ourapps.mymovielist.R

class MovieListAdapter(private val listMovie : ArrayList<SearchItem>) :
    RecyclerView.Adapter<MovieListAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback :OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val movie = listMovie[position]

        holder.binding.apply {
            tvTitle.text = movie.title
            "${movie.year} • ${movie.type}".also { tvInfo.text = it }

            Glide.with(root.context)
                .load(movie.poster)
                .placeholder(R.drawable.no_poster)
                .into(poster)
        }

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(movie) }
    }

    override fun getItemCount(): Int = listMovie.size

    interface OnItemClickCallback {
        fun onItemClicked(user: SearchItem)
    }

}