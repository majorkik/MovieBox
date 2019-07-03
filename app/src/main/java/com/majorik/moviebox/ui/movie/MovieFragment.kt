package com.majorik.moviebox.ui.movie

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.MovieCollectionAdapter
import com.majorik.moviebox.adapters.MovieCollectionSliderAdapter
import com.majorik.moviebox.extensions.CardsPagerTransformerShift
import com.majorik.moviebox.extensions.setAdapterWithFixedSize
import com.majorik.moviebox.ui.base.BaseNavigationFragment
import com.majorik.moviebox.ui.movieTabCollections.MovieCollectionsActivity
import com.majorik.moviebox.ui.tvTabCollections.TVCollectionsActivity
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_tv.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : BaseNavigationFragment() {

    private val movieViewModel: MovieViewModel by viewModel()

    override fun getLayoutId() = R.layout.fragment_movie

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
        movieViewModel.fetchPopularMovies("", 1, "")
        movieViewModel.fetchTopRatedMovies("", 1, "")
        movieViewModel.fetchNowPlayingMovies("ru", 1, "")
        movieViewModel.fetchUpcomingMovies("", 1, "")
    }

    private fun setClickListeners() {
        more_now_playing_movies.setOnClickListener {
            openNewActivityWithTab(0)
        }
        more_upcoming_movies.setOnClickListener {
            openNewActivityWithTab(1)
        }
        more_popular_movies.setOnClickListener {
            openNewActivityWithTab(2)
        }
        more_top_rated_movies.setOnClickListener {
            openNewActivityWithTab(3)
        }
    }

    override fun setObservers() {
        movieViewModel.popularMoviesLiveData.observe(this, Observer {
            list_popular_movies.setAdapterWithFixedSize(MovieCollectionAdapter(it), true)
        })

        movieViewModel.topRatedMoviesLiveData.observe(this, Observer {
            list_top_rated_movies.setAdapterWithFixedSize(MovieCollectionAdapter(it), true)
        })

        movieViewModel.nowPlayingMoviesLiveData.observe(this, Observer {
            now_playing_movies_slider.adapter = MovieCollectionSliderAdapter(it)
            val screen = Point()
            activity?.windowManager?.defaultDisplay?.getSize(screen)
            val startOffset = (32.0 / (screen.x - 2.0 * 32.0))
            now_playing_movies_slider.pageMargin = ((16 * resources.displayMetrics.density).toInt())
            now_playing_movies_slider.setPageTransformer(
                false,
                CardsPagerTransformerShift(24, 8, 0.75F, startOffset.toFloat())
            )
        })

        movieViewModel.upcomingMoviesLiveData.observe(this, Observer {
            list_upcoming_movies.setAdapterWithFixedSize(MovieCollectionAdapter(it), true)
        })
    }

    private fun openNewActivityWithTab(numTab: Int) {
        val intent = Intent(context, MovieCollectionsActivity::class.java)

        intent.putExtra("page", numTab)

        startActivity(intent)
    }
}