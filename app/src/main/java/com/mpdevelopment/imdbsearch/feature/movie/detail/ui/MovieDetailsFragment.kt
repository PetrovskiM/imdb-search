package com.mpdevelopment.imdbsearch.feature.movie.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mpdevelopment.imdbsearch.R
import com.mpdevelopment.imdbsearch.databinding.FragmentMovieDetailsBinding
import com.mpdevelopment.imdbsearch.feature.movie.detail.state.MovieDetailsState
import com.mpdevelopment.imdbsearch.feature.movie.detail.viewmodel.MovieDetailsViewModel
import com.mpdevelopment.imdbsearch.model.MovieDetails
import com.mpdevelopment.imdbsearch.model.Poster
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeStates()
        viewModel.loadMovie(args.id, args.name)
        binding = FragmentMovieDetailsBinding.inflate(inflater)
        return binding.root
    }

    private fun observeStates() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { handleState(it) }
        }
    }

    private fun handleState(state: MovieDetailsState) {
        when (state) {
            is MovieDetailsState.Display -> showMovieState(state.movie)
            MovieDetailsState.Error -> showEmptyState()
            MovieDetailsState.Loading -> showLoadingState()
        }
    }

    private fun showEmptyState() {
        hideLoadingState()
        binding.errorTextView.visibility = View.VISIBLE
    }

    private fun showLoadingState() {
        binding.loadingProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingState() {
        binding.loadingProgressBar.visibility = View.GONE
    }

    private fun showMovieState(movie: MovieDetails) {
        hideLoadingState()
        movie.image?.let { loadImage(it) }
        movie.runningTimeMinutes?.let {
            binding.durationTextView.apply {
                text =
                    String.format(getString(R.string.movie_details_running_time), it)
                visibility = View.VISIBLE
            }
        }
        binding.titleTextView.apply {
            text = movie.title
            visibility = View.VISIBLE
        }
        movie.type?.let {
            binding.typeTextView.apply {
                text = it.name
                visibility = View.VISIBLE
            }
        }
    }

    private fun loadImage(poster: Poster) {
        val requestOptions = RequestOptions().centerInside().transform(
            CenterInside(),
            GranularRoundedCorners(0f, 0f, 24f, 24f)
        )
        binding.posterImageView.visibility = View.VISIBLE
        Glide.with(this)
            .load(poster.url)
            .placeholder(R.drawable.ic_movie)
            .apply(requestOptions)
            .into(binding.posterImageView)
    }
}