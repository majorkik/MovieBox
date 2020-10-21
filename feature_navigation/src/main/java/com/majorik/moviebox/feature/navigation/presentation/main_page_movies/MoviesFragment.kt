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
import com.majorik.library.base.base.UpdateMargins
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.GenresConstants
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.enums.GenresType
import com.majorik.library.base.enums.SELECTED_GENRES_TYPE
import com.majorik.library.base.extensions.*
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.library.base.utils.GenresStorageObject
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.data.repositories.TrendingRepository.TimeWindow
import com.majorik.moviebox.feature.navigation.databinding.FragmentMoviesBinding
import com.majorik.moviebox.feature.navigation.domain.movie.MovieCollectionType
import com.majorik.moviebox.feature.navigation.domain.movie.MovieCollectionType.*
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.genre.GenreResponse
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.MovieResponse
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.person.PersonResponse
import com.majorik.moviebox.feature.navigation.domain.youtubeModels.SearchResponse
import com.majorik.moviebox.feature.navigation.presentation.adapters.*
import com.orhanobut.logger.Logger
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.majorik.moviebox.R as AppResources

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val viewBinding: FragmentMoviesBinding by viewBinding()

    private val viewModel: MoviesViewModel by viewModel()

    /**
     * Adapters
     */

    private val nowPlayingMoviesAdapter = MovieCollectionAdapter(::actionClickMovie)
    private val upcomingMoviesAdapter = MovieDateCardAdapter(::actionClickMovie)
    private val trailersAdapter = TrailersAdapter()
    private val peopleAdapter = PersonAdapter()
    private val popularMoviesAdapter = MovieCardAdapter(::actionClickMovie)
    private val genresAdapter = GenreAdapter()
    private val trendingMovieAdapter = MovieTrendAdapter(::actionClickMovie)

    /**
     * Default methods
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.setDarkNavigationBarColor()

        setupAdapters()
        fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return super.onCreateView(inflater, container, savedInstanceState)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(AppResources.menu.main_toolbar_menu, menu)
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

    private fun setupAdapters() {
        popularMoviesAdapter.setHasStableIds(true)
        nowPlayingMoviesAdapter.setHasStableIds(true)
        upcomingMoviesAdapter.setHasStableIds(true)
        trailersAdapter.setHasStableIds(true)
        peopleAdapter.setHasStableIds(true)
        genresAdapter.setHasStableIds(true)
        trendingMovieAdapter.setHasStableIds(true)
    }

    private fun fetchData() {
        viewModel.run {
            fetchPopularMovies(AppConfig.REGION, 1, "")
            fetchNowPlayingMovies(AppConfig.REGION, 1, "RU")
            fetchTrendingMovies(TimeWindow.WEEK, 1, AppConfig.REGION)
            fetchUpcomingMovies(AppConfig.REGION, 1, "RU")
            fetchMovieGenres(AppConfig.REGION)
            searchYouTubeVideosByChannel()
            fetchPopularPeoples(AppConfig.REGION, 1)
        }
    }

    private fun setClickListeners() {
        viewBinding.apply {
            btnSearch.setOnClickListener {
                findNavController().navigate(MoviesFragmentDirections.actionNavMoviesToNavDetails(458888))
            }

            btnPopularMovies.setOnClickListener { openNewActivityWithTab(ScreenLinks.movieCollection, POPULAR) }

            btnUpcomingMovies.setOnClickListener {
                openNewActivityWithTab(
                    ScreenLinks.movieCollection,
                    UPCOMING
                )
            }

            btnNowPlayingMovies.setOnClickListener {
                openNewActivityWithTab(
                    ScreenLinks.movieCollection,
                    NOW_PLAYING
                )
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
            popularMoviesLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    setPopularMovies(it)
                }
            )

            nowPlayingMoviesLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    setNowPlayingMovies(it)
                }
            )

            trendingMoviesLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    setTrendingMovies(it)
                }
            )

            upcomingMoviesLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    setUpcomingMovies(it)
                }
            )

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

            popularPeoplesLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    setPopularPeoples(it)
                }
            )
        }
    }

    private fun openNewActivityWithTab(
        collectionsActivity: String,
        collectionType: MovieCollectionType
    ) {
        val intent = Intent().setClassName(requireContext(), collectionsActivity)

        intent.putExtra("collection_name", collectionType.name)

        startActivity(intent)
    }

    /**
     * Actions
     */

    private fun actionClickMovie(id: Int) {
        findNavController().navigate(MoviesFragmentDirections.actionNavMoviesToNavDetails(id))
    }

    /**
     * Setters
     */

    private fun setPopularMovies(result: ResultWrapper<MovieResponse>) {
        when (result) {
            is ResultWrapper.Success -> {
                popularMoviesAdapter.addItems(result.value.results)
            }
        }
    }

    private fun setNowPlayingMovies(result: ResultWrapper<MovieResponse>?) {
        when (result) {
            is ResultWrapper.Success -> {
                nowPlayingMoviesAdapter.addItems(result.value.results)
            }
        }
    }

    private fun setTrendingMovies(result: ResultWrapper<MovieResponse>?) {
        when (result) {
            is ResultWrapper.Success -> {
                if (GenresStorageObject.movieGenres.isEmpty()) {
                    GenresConstants.movieGenres.forEach {
                        GenresStorageObject.movieGenres.put(it.id, it.name)
                    }
                }

                trendingMovieAdapter.addItems(result.value.results)
            }
        }
    }

    private fun setUpcomingMovies(result: ResultWrapper<MovieResponse>) {
        when (result) {
            is ResultWrapper.Success -> {
                val items = result.value.results.sortedBy { it.releaseDate?.toDate()?.utc?.unixMillisLong ?: 0L }

                upcomingMoviesAdapter.addItems(items)
            }
        }
    }

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

    private fun setPopularPeoples(result: ResultWrapper<PersonResponse>?) {
        when (result) {
            is ResultWrapper.Success -> {
                peopleAdapter.addItems(result.value.results)
            }
        }
    }
}
