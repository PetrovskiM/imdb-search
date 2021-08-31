package com.mpdevelopment.imdbsearch.feature.movie.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mpdevelopment.imdbsearch.R
import com.mpdevelopment.imdbsearch.databinding.MovieBasicsItemViewBinding
import com.mpdevelopment.imdbsearch.model.MovieBasics

/** Check out [common.binder] [ViewBinder] [BinderViewHolder] [ViewBinderAdapter] for an interesting
 * ViewBinder and ViewBinderAdapter which allows you in the same recyclerview
 * to have multiple items with custom binders in any order that the list of items has them in
 * we declare and store these binders and the implementation calls on those binders for the specific item that came
 * Ex: We have Movie, TvShow, Ad, Ad, Movie, TvShow, they will all show up in that order with custom binders
 * But here for time sake and because there is no need we use a standard Rv*/
class MovieListAdapter(
    private var movieBasics: List<MovieBasics>,
    private val callback: OnItemClickListener
) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(movie: MovieBasics)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MovieBasicsItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieBasics[position]
        holder.apply {
            binding.posterImageView.apply {
                Glide.with(context)
                    .load(movie.poster?.url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_movie)
                    .into(this)

                transitionName = context.getString(R.string.movies_list_item_image_transition)
            }
            binding.nameTextView.text = movie.name
            binding.actorsTextView.text = movie.featuredActors
            binding.yearTextView.text =
                if (!movie.yearsActive.isNullOrBlank()) movie.yearsActive else movie.year.toString()
            binding.rankTextView.text = movie.rank.toString()
        }
        holder.setClickListener(movie)
    }

    override fun getItemCount(): Int = movieBasics.size

    fun updateMovies(movies: List<MovieBasics>) {
        movieBasics = movies
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: MovieBasicsItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setClickListener(movie: MovieBasics) {
            binding.rootCardView.setOnClickListener {
                callback.onItemClick(movie)
            }
        }
    }
}