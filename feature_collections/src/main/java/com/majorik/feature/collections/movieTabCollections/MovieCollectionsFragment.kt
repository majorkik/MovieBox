package com.majorik.feature.collections.movieTabCollections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.majorik.feature.collections.domain.NetworkState
import com.majorik.feature.collections.domain.movie.MovieCollectionType
import com.majorik.moviebox.R
import com.majorik.feature.collections.presentation.adapters.PagingMovieCollectionAdapter
import com.majorik.library.base.extensions.toPx
import com.majorik.library.base.utils.SpacingDecoration
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_collection_page.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieCollectionsFragment(collectionType: MovieCollectionType) : Fragment(),
    PagingMovieCollectionAdapter.OnClickListener {

    private val movieViewModel: MovieCollectionsViewModel by viewModel {
        parametersOf(collectionType)
    }

    private lateinit var adapter: PagingMovieCollectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.majorik.feature.collections.R.layout.fragment_collection_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        configureObservables()
        configureOnClick()
    }

    private fun configureOnClick() {
        movie_empty_list_button.setOnClickListener { movieViewModel.refreshAllList() }
    }

    private fun configureRecyclerView() {
        adapter = PagingMovieCollectionAdapter(this)
        grid_items.addItemDecoration(SpacingDecoration(16.toPx(), 16.toPx(), true))
        grid_items.adapter = ScaleInAnimationAdapter(adapter)
    }

    private fun configureObservables() {
        movieViewModel.networkState?.observe(viewLifecycleOwner, Observer {
            adapter.updateNetworkState(it)
        })

        movieViewModel.movieResults.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onClickRetry() {
        movieViewModel.refreshFailedRequest()
    }

    override fun whenListIsUpdated(size: Int, networkState: NetworkState?) {
        updateUIWhenLoading(size, networkState)
        updateUIWhenEmptyList(size, networkState)
    }

    private fun updateUIWhenEmptyList(size: Int, networkState: NetworkState?) {
        movie_empty_list_image.visibility = View.GONE
        movie_empty_list_button.visibility = View.GONE
        movie_empty_list_title.visibility = View.GONE
        if (size == 0) {
            when (networkState) {
                NetworkState.SUCCESS -> {
                    movie_empty_list_title.text = getString(R.string.no_result_found)
                    movie_empty_list_image.visibility = View.VISIBLE
                    movie_empty_list_title.visibility = View.VISIBLE
                    movie_empty_list_button.visibility = View.GONE
                }
                NetworkState.FAILED -> {
                    movie_empty_list_title.text = getString(R.string.technical_error)
                    movie_empty_list_image.visibility = View.VISIBLE
                    movie_empty_list_title.visibility = View.VISIBLE
                    movie_empty_list_button.visibility = View.VISIBLE
                }
                else -> {
                }
            }
        }
    }

    private fun updateUIWhenLoading(size: Int, networkState: NetworkState?) {
        movie_empty_list_progressbar.visibility =
            if (size == 0 && networkState == NetworkState.RUNNING) View.VISIBLE else View.GONE
    }
}
