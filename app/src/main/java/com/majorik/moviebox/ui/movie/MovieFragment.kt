package com.majorik.moviebox.ui.movie

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.CollectionAdapter
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {
    private val movieViewModel: MovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_movie, container, false)

        setHasOptionsMenu(true)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel.fetchPopularMovies("", 1, "")
        movieViewModel.fetchTopRatedMovies("", 1, "")

        movieViewModel.popularMoviesLiveData.observe(this, Observer {
            list_popular_movies.adapter = CollectionAdapter(it)
        })

        movieViewModel.topRatedMoviesLiveData.observe(this, Observer {
            list_top_rated_movies.adapter = CollectionAdapter(it)
        })
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_toolbar_menu, menu)
    }
}