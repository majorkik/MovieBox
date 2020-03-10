package com.majorik.moviebox.ui.main_page_movies

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.util.isEmpty
import androidx.lifecycle.Observer
import com.majorik.data.repositories.TrendingRepository
import com.majorik.domain.constants.GenresConstants
import com.majorik.domain.enums.movie.MovieCollectionType
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.*
import com.majorik.moviebox.extensions.setAdapterWithFixedSize
import com.majorik.moviebox.extensions.startActivityWithAnim
import com.majorik.moviebox.extensions.toDate
import com.majorik.moviebox.extensions.toPx
import com.majorik.moviebox.ui.base.BaseNavigationFragment
import com.majorik.moviebox.ui.movieTabCollections.MovieCollectionsActivity
import com.majorik.moviebox.ui.search.SearchableActivity
import com.majorik.moviebox.utils.GenresStorageObject
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : BaseNavigationFragment() {

    private val moviesViewModel: MoviesViewModel by viewModel()

    override fun getLayoutId() = R.layout.fragment_movies

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData()
        setObservers()
        setClickListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_toolbar_menu, menu)
    }

    override fun fetchData() {
        moviesViewModel.fetchMovieGenres("ru-RU")
        moviesViewModel.fetchPopularMovies("ru-RU", 1, "")
        moviesViewModel.fetchNowPlayingMovies("ru-RU", 1, "RU")
        moviesViewModel.fetchUpcomingMovies("ru-RU", 1, "RU")
        moviesViewModel.searchYouTubeVideosByChannel()
        moviesViewModel.fetchTrendingMovies(TrendingRepository.TimeWindow.WEEK, 1, "ru-RU")
        moviesViewModel.fetchPopularPeoples("ru-RU", 1)
    }

    private fun setClickListeners() {
        btn_search.setOnClickListener {
            context?.startActivityWithAnim(SearchableActivity::class.java)
        }

        btn_popular_movies.setOnClickListener {
            openNewActivityWithTab(
                MovieCollectionsActivity::class.java,
                MovieCollectionType.POPULAR
            )
        }

        btn_upcoming_movies.setOnClickListener {
            openNewActivityWithTab(
                MovieCollectionsActivity::class.java,
                MovieCollectionType.UPCOMING
            )
        }

        btn_now_playing_movies.setOnClickListener {
            openNewActivityWithTab(
                MovieCollectionsActivity::class.java,
                MovieCollectionType.NOW_PLAYING
            )
        }
    }

    override fun setObservers() {
        moviesViewModel.popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
            vp_popular_movies.run {
                adapter = MovieCardAdapter(it)
                pageMargin = 16.toPx()
            }
        })

        moviesViewModel.upcomingMoviesLiveData.observe(viewLifecycleOwner, Observer {
            rv_upcoming_movies.setAdapterWithFixedSize(MovieDateCardAdapter(it.sortedBy {
                it.releaseDate?.toDate()?.utc?.unixMillisLong ?: 0L
            }), true)
        })

        moviesViewModel.nowPlayingMoviesLiveData.observe(viewLifecycleOwner, Observer {
            rv_now_playing_movies.setAdapterWithFixedSize(MovieCollectionAdapter(it), true)
        })

        moviesViewModel.genresLiveData.observe(viewLifecycleOwner, Observer {
            if (GenresStorageObject.movieGenres.isEmpty()) {
                it.map { genre -> GenresStorageObject.movieGenres.put(genre.id, genre.name) }
            }

            rv_movie_genres.adapter = GenreAdapter(it)
        })

        moviesViewModel.popularPeoplesLiveData.observe(viewLifecycleOwner, Observer {
            rv_trending_peoples.adapter = PersonAdapter(it)
        })

        moviesViewModel.trailersLiveData.observe(viewLifecycleOwner, Observer {
            rv_trailers.adapter = TrailersAdapter(it)
        })

        moviesViewModel.trendingMoviesLiveData.observe(viewLifecycleOwner, Observer {
            if (GenresStorageObject.movieGenres.isEmpty()) {
                GenresConstants.movieGenres.forEach {
                    GenresStorageObject.movieGenres.put(it.id, it.name)
                }
            }

            vp_trend_movies.run {
                adapter = MovieTrendAdapter(it)
                pageMargin = 16.toPx()
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
