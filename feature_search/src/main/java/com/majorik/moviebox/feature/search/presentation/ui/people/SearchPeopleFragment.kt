package com.majorik.moviebox.feature.search.presentation.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.majorik.moviebox.feature.search.presentation.adapters.search.SearchPeopleAdapter
import com.majorik.moviebox.feature.search.domain.NetworkState
import com.majorik.library.base.extensions.toPx
import com.majorik.moviebox.feature.search.presentation.ui.SearchQueryChangeListener
import com.majorik.moviebox.feature.search.presentation.ui.SearchViewTypeChangeListener
import com.majorik.library.base.utils.SpacingDecoration
import com.majorik.moviebox.feature.search.presentation.adapters.PaginationOnClickListener
import kotlinx.android.synthetic.main.fragment_searchable.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SearchPeopleFragment : Fragment(), SearchQueryChangeListener,
    PaginationOnClickListener, SearchViewTypeChangeListener {

    private val searchViewModel: SearchPeopleViewModel by viewModel()

    private lateinit var adapter: SearchPeopleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.majorik.moviebox.feature.search.R.layout.fragment_searchable, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        configureObservables()
        configureOnClick()
    }

    override fun queryChange(query: String) {
        searchViewModel.fetchItems(query)
    }

    private fun configureOnClick() {
//        emptyListButton.setOnClickListener { searchableViewModel.refreshAllList() }
    }

    private fun configureRecyclerView() {
        adapter = SearchPeopleAdapter(this)
        search_list.adapter = adapter

        search_list.addItemDecoration(SpacingDecoration(16.toPx(), 16.toPx(), true))
    }

    private fun configureObservables() {
        searchViewModel.networkState?.observe(
            viewLifecycleOwner,
            Observer { adapter.updateNetworkState(it) })
        searchViewModel.searchResults.observe(
            viewLifecycleOwner,
            Observer { adapter.submitList(it) })
    }

    private fun updateUIWhenEmptyList(size: Int, networkState: NetworkState?) {

        if (size == 0) {
            when (networkState) {
                NetworkState.SUCCESS -> {
                }
                NetworkState.FAILED -> {
                }
                NetworkState.RUNNING -> {
                }
                null -> {
                }
            }
        }
    }

    private fun updateUIWhenLoading(size: Int, networkState: NetworkState?) {
    }

    override fun onClickRetry() {
        searchViewModel.refreshFailedRequest()
    }

    override fun whenListIsUpdated(size: Int, networkState: NetworkState?) {
        updateUIWhenLoading(size, networkState)
        updateUIWhenEmptyList(size, networkState)
    }

    override fun changeViewType(isGrid: Boolean) {
        if (isGrid) {
            search_list.layoutManager = GridLayoutManager(context, 4)
        } else {
            search_list.layoutManager = GridLayoutManager(context, 1)
        }

        adapter.setViewType(isGrid)
    }
}
