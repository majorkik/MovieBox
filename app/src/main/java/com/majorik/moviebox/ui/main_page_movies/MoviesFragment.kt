package com.majorik.moviebox.ui.main_page_movies

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.util.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.majorik.data.repositories.TrendingRepository.*
import com.majorik.domain.constants.AppConfig
import com.majorik.domain.constants.GenresConstants
import com.majorik.domain.enums.movie.MovieCollectionType
import com.majorik.domain.enums.movie.MovieCollectionType.*
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.*
import com.majorik.moviebox.adapters.movie.*
import com.majorik.moviebox.extensions.*
import com.majorik.moviebox.ui.base.BaseNavigationFragment
import com.majorik.moviebox.ui.genres.GenresActivity
import com.majorik.moviebox.ui.movieTabCollections.MovieCollectionsActivity
import com.majorik.moviebox.ui.search.SearchableActivity
import com.majorik.moviebox.utils.GenresStorageObject
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : BaseNavigationFragment() {

    private val moviesViewModel: MoviesViewModel by viewModel()

    private val nowPlayingMoviesAdapter = MovieCollectionAdapter()
    private val upcomingMoviesAdapter = MovieDateCardAdapter()
    private val trailersAdapter = TrailersAdapter()
    private val peopleAdapter = PersonAdapter()
    private val popularMoviesAdapter = MovieCardAdapter()

    override fun getLayoutId() = R.layout.fragment_movies

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
        }
    }

    override fun fetchData() {
        lifecycleScope.launchWhenCreated {
            moviesViewModel.fetchPopularMovies(AppConfig.REGION, 1, "")

            moviesViewModel.fetchNowPlayingMovies(AppConfig.REGION, 1, "RU")

            moviesViewModel.fetchTrendingMovies(TimeWindow.WEEK, 1, AppConfig.REGION)

            moviesViewModel.fetchUpcomingMovies(AppConfig.REGION, 1, "RU")

            moviesViewModel.fetchMovieGenres(AppConfig.REGION)

            moviesViewModel.searchYouTubeVideosByChannel()

            moviesViewModel.fetchPopularPeoples(AppConfig.REGION, 1)
        }
    }

    private fun setClickListeners() {
        btn_search.setOnClickListener {
            context?.startActivityWithAnim(SearchableActivity::class.java)
        }

        btn_popular_movies.setOnClickListener {
            openNewActivityWithTab(MovieCollectionsActivity::class.java, POPULAR)
        }

        btn_upcoming_movies.setOnClickListener {
            openNewActivityWithTab(MovieCollectionsActivity::class.java, UPCOMING)
        }

        btn_now_playing_movies.setOnClickListener {
            openNewActivityWithTab(MovieCollectionsActivity::class.java, NOW_PLAYING)
        }

        btn_movie_genres.setSafeOnClickListener {
            val intent = Intent(context, GenresActivity::class.java)

            intent.putExtra(
                GenresActivity.SELECTED_GENRES_TYPE,
                GenresActivity.GenresType.MOVIE_GENRES
            )

            context?.startActivityWithAnim(intent)
        }
    }

    override fun setObservers() {
        moviesViewModel.popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
            popularMoviesAdapter.addItems(it)
        })

        moviesViewModel.nowPlayingMoviesLiveData.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launchWhenCreated {
                delay(150)

                nowPlayingMoviesAdapter.addItems(it)
            }
        })

        moviesViewModel.trendingMoviesLiveData.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launchWhenCreated {
                if (GenresStorageObject.movieGenres.isEmpty()) {
                    GenresConstants.movieGenres.forEach {
                        GenresStorageObject.movieGenres.put(it.id, it.name)
                    }
                }

                delay(300)

                vp_trend_movies.run {
                    adapter = MovieTrendAdapter(it)
                    pageMargin = 16.toPx()
                }
            }
        })

        moviesViewModel.upcomingMoviesLiveData.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                val items = it.sortedBy { it.releaseDate?.toDate()?.utc?.unixMillisLong ?: 0L }

                delay(500)

                upcomingMoviesAdapter.addItems(items)
            }
        })

        moviesViewModel.genresLiveData.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launchWhenCreated {
                if (GenresStorageObject.movieGenres.isEmpty()) {
                    it.map { genre -> GenresStorageObject.movieGenres.put(genre.id, genre.name) }
                }

                delay(700)

                rv_movie_genres.setAdapterWithFixedSize(GenreAdapter(it), true)
            }
        })

        moviesViewModel.trailersLiveData.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launchWhenCreated {
                delay(900)

                trailersAdapter.addItems(it)
            }
        })

        moviesViewModel.popularPeoplesLiveData.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launchWhenCreated {
                delay(1100)

                peopleAdapter.addItems(it)
            }
        })
    }

    private fun openNewActivityWithTab(
        collectionsActivity: Class<*>,
        collectionType: MovieCollectionType
    ) {
        val intent = Intent(context, collectionsActivity)

        intent.putExtra("collection_name", collectionType.name)

        startActivity(intent)
    }

    companion object {
        fun newInstance() = MoviesFragment()
    }
}