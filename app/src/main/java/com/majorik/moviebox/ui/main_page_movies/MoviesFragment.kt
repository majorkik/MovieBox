package com.majorik.moviebox.ui.main_page_movies

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.MovieCardAdapter
import com.majorik.moviebox.adapters.MovieCollectionAdapter
import com.majorik.moviebox.extensions.setAdapterWithFixedSize
import com.majorik.moviebox.ui.base.BaseNavigationFragment
import com.majorik.moviebox.ui.movieTabCollections.MovieCollectionsActivity
import com.majorik.moviebox.ui.search.SearchableActivity
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
        moviesViewModel.fetchPopularMovies("ru-RU", 1, "")
        moviesViewModel.fetchUpcomingMovies("ru-RU", 1, "")
        moviesViewModel.fetchNowPlayingMovies("ru-RU", 1, "")
    }

    private fun setClickListeners() {
        btn_search.setOnClickListener {
            startActivity(Intent(context, SearchableActivity::class.java))
        }

        btn_popular_movies.setOnClickListener {
            openNewActivityWithTab(MovieCollectionsActivity::class.java, 0)
        }

        btn_upcoming_movies.setOnClickListener {
            openNewActivityWithTab(MovieCollectionsActivity::class.java, 1)
        }

        btn_now_playing_movies.setOnClickListener {
            openNewActivityWithTab(MovieCollectionsActivity::class.java, 2)
        }
    }

    override fun setObservers() {
        moviesViewModel.popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
            vp_popular_movies.run {
                adapter = MovieCardAdapter(it)
                pageMargin = ((16 * resources.displayMetrics.density).toInt())
            }
        })

        moviesViewModel.upcomingMoviesLiveData.observe(viewLifecycleOwner, Observer {
            rv_upcoming_movies.setAdapterWithFixedSize(MovieCollectionAdapter(it), true)
        })

        moviesViewModel.nowPlayingMoviesLiveData.observe(viewLifecycleOwner, Observer {
            rv_now_playing_movies.setAdapterWithFixedSize(MovieCollectionAdapter(it), true)
        })
    }

    private fun openNewActivityWithTab(collectionsActivity: Class<*>, numTab: Int) {
        val intent = Intent(context, collectionsActivity)

        intent.putExtra("page", numTab)

        startActivity(intent)
    }
}