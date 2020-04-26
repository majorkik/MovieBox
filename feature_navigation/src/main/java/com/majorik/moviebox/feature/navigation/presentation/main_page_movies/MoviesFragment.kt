package com.majorik.moviebox.feature.navigation.presentation.main_page_movies

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.util.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.majorik.moviebox.feature.navigation.data.repositories.TrendingRepository.*
import com.majorik.moviebox.feature.navigation.domain.movie.MovieCollectionType
import com.majorik.moviebox.feature.navigation.domain.movie.MovieCollectionType.*
import com.majorik.moviebox.feature.navigation.presentation.adapters.*
import com.majorik.moviebox.R
import com.majorik.library.base.extensions.*
import com.majorik.library.base.base.BaseNavigationFragment
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.GenresConstants
import com.majorik.library.base.utils.GenresStorageObject
import com.majorik.moviebox.feature.navigation.presentation.adapters.MovieTrendAdapter
import com.majorik.moviebox.feature.navigation.presentation.main_page_movies.MoviesViewModel
import com.majorik.moviebox.feature.navigation.presentation.adapters.TrailersAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : BaseNavigationFragment() {

    private val moviesViewModel: MoviesViewModel by viewModel()

    private val nowPlayingMoviesAdapter = MovieCollectionAdapter()
    private val upcomingMoviesAdapter = MovieDateCardAdapter()
    private val trailersAdapter = TrailersAdapter()
    private val peopleAdapter = PersonAdapter()
    private val popularMoviesAdapter = MovieCardAdapter()
    private val genresAdapter = GenreAdapter()
    private val trendingMovieAdapter = MovieTrendAdapter()

    override fun getLayoutId() = com.majorik.moviebox.feature.navigation.R.layout.fragment_movies

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapters()
        fetchData()
        setObservers()
        setClickListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_toolbar_menu, menu)
    }

    private fun initAdapters() {
        lifecycleScope.launchWhenCreated {
            popularMoviesAdapter.setHasStableIds(true)
            vp_popular_movies.setShowSideItems(16.toPx(), 16.toPx())
            vp_popular_movies.adapter = ScaleInAnimationAdapter(popularMoviesAdapter)

            nowPlayingMoviesAdapter.setHasStableIds(true)
            rv_now_playing_movies.setAdapterWithFixedSize(
                ScaleInAnimationAdapter(nowPlayingMoviesAdapter),
                true
            )

            upcomingMoviesAdapter.setHasStableIds(true)
            rv_upcoming_movies.setAdapterWithFixedSize(
                ScaleInAnimationAdapter(upcomingMoviesAdapter),
                true
            )

            trailersAdapter.setHasStableIds(true)
            rv_trailers.setAdapterWithFixedSize(ScaleInAnimationAdapter(trailersAdapter), true)

            peopleAdapter.setHasStableIds(true)
            rv_trending_peoples.setAdapterWithFixedSize(
                ScaleInAnimationAdapter(peopleAdapter),
                false
            )

            genresAdapter.setHasStableIds(true)
            rv_movie_genres.setAdapterWithFixedSize(genresAdapter, true)

            trendingMovieAdapter.setHasStableIds(true)
            vp_trend_movies.adapter = trendingMovieAdapter
        }
    }

    override fun fetchData() {
        moviesViewModel.run {
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
        btn_search.setOnClickListener {
            context?.startActivityWithAnim("com.majorik.moviebox.feature.search.presentation.search.SearchableActivity")
        }

        btn_popular_movies.setOnClickListener {
            openNewActivityWithTab(
                "com.majorik.moviebox.feature.collections.movieTabCollections.MovieCollectionsActivity",
                POPULAR
            )
        }

        btn_upcoming_movies.setOnClickListener {
            openNewActivityWithTab(
                "com.majorik.moviebox.feature.collections.movieTabCollections.MovieCollectionsActivity",
                UPCOMING
            )
        }

        btn_now_playing_movies.setOnClickListener {
            openNewActivityWithTab(
                "com.majorik.moviebox.feature.collections.movieTabCollections.MovieCollectionsActivity",
                NOW_PLAYING
            )
        }

        btn_movie_genres.setSafeOnClickListener {
            val intent =
                Intent().setClassName(requireContext(), "com.majorik.moviebox.feature.collections.genres.GenresActivity")

            //TODO
//            intent.putExtra(
//                GenresActivity.SELECTED_GENRES_TYPE,
//                GenresActivity.GenresType.MOVIE_GENRES
//            )

            context?.startActivityWithAnim(intent)
        }
    }

    override fun setObservers() {
        moviesViewModel.run {
            popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
                popularMoviesAdapter.addItems(it)
            })

            nowPlayingMoviesLiveData.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launchWhenCreated {
                    delay(150)

                    nowPlayingMoviesAdapter.addItems(it)
                }
            })

            trendingMoviesLiveData.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launchWhenCreated {
                    if (GenresStorageObject.movieGenres.isEmpty()) {
                        GenresConstants.movieGenres.forEach {
                            GenresStorageObject.movieGenres.put(it.id, it.name)
                        }
                    }

                    delay(300)

                    trendingMovieAdapter.addItems(it)
                }
            })

            upcomingMoviesLiveData.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launch {
                    val items = it.sortedBy { it.releaseDate?.toDate()?.utc?.unixMillisLong ?: 0L }

                    delay(500)

                    upcomingMoviesAdapter.addItems(items)
                }
            })

            genresLiveData.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launchWhenCreated {
                    if (GenresStorageObject.movieGenres.isEmpty()) {
                        it.map { genre ->
                            GenresStorageObject.movieGenres.put(
                                genre.id,
                                genre.name
                            )
                        }
                    }

                    delay(700)

                    genresAdapter.addItems(it)
                }
            })

            trailersLiveData.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launchWhenCreated {
                    delay(900)

                    trailersAdapter.addItems(it)
                }
            })

            popularPeoplesLiveData.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launchWhenCreated {
                    delay(1100)

                    peopleAdapter.addItems(it)
                }
            })
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

    companion object {
        fun newInstance() = MoviesFragment()
    }
}
