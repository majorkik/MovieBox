package com.majorik.moviebox.ui.movie

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.CollectionAdapter
import com.majorik.moviebox.ui.base.BaseNavigationFragment
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : BaseNavigationFragment() {

    private val movieViewModel: MovieViewModel by viewModel()

    override fun getLayoutId() = R.layout.fragment_movie

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData()
        setObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_toolbar_menu, menu)
    }

    override fun fetchData() {
        movieViewModel.fetchPopularMovies("", 1, "")
        movieViewModel.fetchTopRatedMovies("", 1, "")
    }

    override fun setObservers() {
        movieViewModel.popularMoviesLiveData.observe(this, Observer {
            list_popular_movies.adapter = CollectionAdapter(it)
        })

        movieViewModel.topRatedMoviesLiveData.observe(this, Observer {
            list_top_rated_movies.adapter = CollectionAdapter(it)
        })
    }

}