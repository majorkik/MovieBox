package com.majorik.moviebox.feature.navigation.presentation.main_page_movies

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.util.isEmpty
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.enums.GenresType
import com.majorik.library.base.enums.SELECTED_GENRES_TYPE
import com.majorik.library.base.extensions.*
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.library.base.utils.GenresStorageObject
import com.majorik.moviebox.domain.enums.collections.MovieCollectionType.*
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.databinding.FragmentMoviesBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.genre.GenreResponse
import com.majorik.moviebox.feature.navigation.domain.youtubeModels.SearchResponse
import com.majorik.moviebox.feature.navigation.presentation.adapters.*
import com.majorik.moviebox.feature.navigation.presentation.main_page_movies.adapters.MovieCardAdapter
import com.majorik.moviebox.feature.navigation.presentation.main_page_movies.adapters.MovieCollectionAdapter
import com.majorik.moviebox.feature.navigation.presentation.main_page_movies.adapters.MovieDateCardAdapter
import com.majorik.moviebox.feature.navigation.presentation.main_page_movies.adapters.MovieTrendAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

typealias Directions = MoviesFragmentDirections

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val viewBinding: FragmentMoviesBinding by viewBinding()

    private val viewModel: MoviesViewModel by viewModel()

    /**
     * Adapters
     */

    private val nowPlayingMoviesAdapter = MovieCollectionAdapter(::actionClickMovie)
    private val upcomingMoviesAdapter = MovieDateCardAdapter(::actionClickMovie)
    private val popularMoviesAdapter = MovieCardAdapter(::actionClickMovie)
    private val trendingMovieAdapter = MovieTrendAdapter(::actionClickMovie)

    private val genresAdapter = GenreAdapter()
    private val trailersAdapter = TrailersAdapter()
    private val peopleAdapter = PersonAdapter()

    /**
     * Default methods
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.setDarkNavigationBarColor()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateMargins()

        initAdapters()
        setObservers()
        setClickListeners()
    }

    private fun updateMargins() {
        viewBinding.root.doOnApplyWindowInsets { view, insets, rect ->
            viewBinding.scrollViewLinearLayout.updatePadding(bottom = insets.systemWindowInsetBottom + 56.px())

            insets
        }
    }

    private fun initAdapters() {
        lifecycleScope.launchWhenCreated {
            viewBinding.vpPopularMovies.setShowSideItems(16.px(), 16.px())
            viewBinding.vpPopularMovies.adapter = popularMoviesAdapter

            viewBinding.rvNowPlayingMovies.setAdapterWithFixedSize(
                ScaleInAnimationAdapter(nowPlayingMoviesAdapter),
                true
            )

            viewBinding.rvUpcomingMovies.setAdapterWithFixedSize(
                upcomingMoviesAdapter,
                true
            )

            viewBinding.rvTrailers.setAdapterWithFixedSize(trailersAdapter, true)

            viewBinding.rvTrendingPeoples.setAdapterWithFixedSize(
                peopleAdapter,
                false
            )

            viewBinding.rvMovieGenres.setAdapterWithFixedSize(genresAdapter, true)

            viewBinding.vpTrendMovies.adapter = trendingMovieAdapter
        }
    }

    private fun setClickListeners() {
        viewBinding.apply {
            btnSearch.setOnClickListener {
                context?.startActivityWithAnim(ScreenLinks.searchableActivity)
            }

            btnPopularMovies.setOnClickListener {
                findNavController().navigate(Directions.actionToMovieCollections(POPULAR))
            }

            btnUpcomingMovies.setOnClickListener {
                findNavController().navigate(Directions.actionToMovieCollections(UPCOMING))
            }

            btnNowPlayingMovies.setOnClickListener {
                findNavController().navigate(Directions.actionToMovieCollections(NOW_PLAYING))
            }

            btnTrendingMovies.setOnClickListener {
                findNavController().navigate(Directions.actionToMovieCollections(POPULAR))
            }

            btnMovieGenres.setSafeOnClickListener {
                context?.startActivityWithAnim(
                    ScreenLinks.genresActivity,
                    Intent().apply {
                        putExtra(SELECTED_GENRES_TYPE, GenresType.MOVIE_GENRES)
                    }
                )
            }
        }
    }

    private fun setObservers() {
        viewModel.run {
            lifecycleScope.launchWhenResumed {
                nowPlayingMoviesFlow.collectLatest { pagingData ->
                    nowPlayingMoviesAdapter.submitData(pagingData)
                }
            }

            lifecycleScope.launchWhenResumed {
                upcomingMoviesFlow.collectLatest { pagingData ->
                    upcomingMoviesAdapter.submitData(pagingData)
                }
            }

            lifecycleScope.launchWhenResumed {
                popularMoviesFlow.collectLatest { pagingData ->
                    popularMoviesAdapter.submitData(pagingData)
                }
            }

            lifecycleScope.launchWhenResumed {
                trendingMoviesFlow.collectLatest { pagingData ->
                    trendingMovieAdapter.submitData(pagingData)
                }
            }

            lifecycleScope.launchWhenResumed {
                viewModel.peoplesFlow.collectLatest { pagingData ->
                    peopleAdapter.submitData(pagingData)
                }
            }

            genresLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    setMovieGenres(it)
                }
            )

            trailersLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    setTrailers(it)
                }
            )
        }
    }

    /**
     * Actions
     */

    private fun actionClickMovie(id: Int) {
        findNavController().navigate(Directions.actionToMovieDetails(id))
    }

    /**
     * Setters
     */

    private fun setMovieGenres(result: ResultWrapper<GenreResponse>?) {
        when (result) {
            is ResultWrapper.Success -> {
                if (GenresStorageObject.movieGenres.isEmpty()) {
                    result.value.genres.map { genre ->
                        GenresStorageObject.movieGenres.put(
                            genre.id,
                            genre.name
                        )
                    }
                }

                genresAdapter.addItems(result.value.genres)
            }
        }
    }

    private fun setTrailers(result: ResultWrapper<SearchResponse>?) {
        when (result) {
            is ResultWrapper.Success -> {
                trailersAdapter.addItems(result.value.items)
            }
        }
    }
}
