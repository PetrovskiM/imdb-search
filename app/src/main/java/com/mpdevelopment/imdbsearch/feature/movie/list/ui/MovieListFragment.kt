package com.mpdevelopment.imdbsearch.feature.movie.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mpdevelopment.imdbsearch.databinding.FragmentMovieListBinding
import com.mpdevelopment.imdbsearch.feature.movie.list.MovieListViewModel
import com.mpdevelopment.imdbsearch.feature.movie.list.state.Action
import com.mpdevelopment.imdbsearch.feature.movie.list.state.MovieListState
import com.mpdevelopment.imdbsearch.model.MovieBasics
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

//Paging3 could have been used if the api supported it or sent alot of data, but i don't really like
//their implementation, its a bit "much", even though mine turned out a bit much too but i was just
//trying things out :)
//Multi module would be overkill for something so small so i decided it to be Monolith, plus
//we all know how much work goes into setting up DI for multi-modules
@AndroidEntryPoint
class MovieListFragment : Fragment(), MovieListAdapter.OnItemClickListener {

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var moviesAdapter: MovieListAdapter
    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater)
        observeStates()
        initSearchView()
        return binding.root
    }

    private fun observeStates() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { handleState(it) }
        }
    }

    private fun handleState(state: MovieListState) {
        when (state) {
            is MovieListState.Display -> showMoviesState(state.movies)
            MovieListState.Empty -> showEmptyState()
            MovieListState.Loading -> showLoadingState()
        }
    }

    /**
     * Could also use data binding, but its debatable if its smart to add logic into xml
     * separation of concern etc.
     */
    private fun showEmptyState() {
        hideLoadingState()
        hideMoviesState()
        binding.noMoviesLinearLayout.visibility = View.VISIBLE
    }

    private fun hideEmptyState() {
        binding.noMoviesLinearLayout.visibility = View.GONE
    }

    private fun showLoadingState() {
        binding.loadingProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingState() {
        binding.loadingProgressBar.visibility = View.GONE
    }

    private fun showMoviesState(movies: List<MovieBasics>) {
        hideLoadingState()
        hideEmptyState()
        initUpdateRecyclerView(movies)
    }

    private fun initUpdateRecyclerView(movies: List<MovieBasics>) {
        binding.moviesRecyclerView.visibility = View.VISIBLE
        if (binding.moviesRecyclerView.adapter != null) {
            moviesAdapter.updateMovies(movies)
        } else {
            moviesAdapter = MovieListAdapter(movies, this)
            binding.moviesRecyclerView.apply {
                adapter = moviesAdapter
                moviesAdapter.stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun hideMoviesState() {
        binding.moviesRecyclerView.visibility = View.GONE
    }

    private fun initSearchView() {
        binding.searchEditText.doAfterTextChanged {
            it?.let { lifecycleScope.launch { viewModel.handleAction(Action.Search(it.toString())) } }
        }
    }

    override fun onItemClick(movie: MovieBasics) {
        val action =
            MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(
                movie.id,
                movie.name
            )
        findNavController().navigate(action)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieListFragment()
    }
}