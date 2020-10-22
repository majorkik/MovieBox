package com.majorik.moviebox.feature.collections.presentation.tvTabCollections

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.majorik.moviebox.feature.collections.domain.NetworkState
import com.majorik.moviebox.domain.enums.collections.TVCollectionType
import com.majorik.moviebox.feature.collections.presentation.adapters.PagingTVCollectionAdapter
import com.majorik.library.base.extensions.px
import com.majorik.library.base.utils.SpacingDecoration
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_collection_page.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import com.majorik.moviebox.feature.collections.R

class TVCollectionsFragment(tvCollectionType: TVCollectionType) :
    Fragment(),
    PagingTVCollectionAdapter.OnClickListener {
    private val tvViewModel: TVCollectionsViewModel by viewModel { parametersOf(tvCollectionType) }
    private lateinit var adapter: PagingTVCollectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collection_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        configureObservables()
        configureOnClick()
    }

    private fun configureOnClick() {
        movie_empty_list_button.setOnClickListener { tvViewModel.refreshAllList() }
    }

    private fun configureRecyclerView() {
        adapter = PagingTVCollectionAdapter(this)
        grid_items.addItemDecoration(SpacingDecoration(16.px(), 16.px(), true))
        grid_items.adapter = ScaleInAnimationAdapter(adapter)
    }

    private fun configureObservables() {
        tvViewModel.networkState?.observe(
            viewLifecycleOwner,
            Observer {
                adapter.updateNetworkState(it)
            }
        )

        tvViewModel.tvResults.observe(
            viewLifecycleOwner,
            Observer {
                adapter.submitList(it)
            }
        )
    }

    override fun onClickRetry() {
        tvViewModel.refreshFailedRequest()
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
                    movie_empty_list_title.text = getString(R.string.collections_no_result_found)
                    movie_empty_list_image.visibility = View.VISIBLE
                    movie_empty_list_title.visibility = View.VISIBLE
                    movie_empty_list_button.visibility = View.GONE
                }
                NetworkState.FAILED -> {
                    movie_empty_list_title.text = getString(R.string.collections_technical_error)
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
