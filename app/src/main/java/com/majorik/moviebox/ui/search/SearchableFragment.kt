package com.majorik.moviebox.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.majorik.domain.NetworkState
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.SearchAdapter
import com.majorik.moviebox.extensions.onQueryTextChange
import kotlinx.android.synthetic.main.fragment_searchable.*
import kotlinx.android.synthetic.main.fragment_searchable.fragment_search_list as recyclerView
import kotlinx.android.synthetic.main.fragment_searchable.movie_empty_list_button as emptyListButton
import kotlinx.android.synthetic.main.fragment_searchable.movie_empty_list_image as emptyListImage
import kotlinx.android.synthetic.main.fragment_searchable.movie_empty_list_progressbar as progressBar
import kotlinx.android.synthetic.main.fragment_searchable.movie_empty_list_title as emptyListTitle
import org.koin.android.viewmodel.ext.android.viewModel

class SearchableFragment : Fragment(), SearchAdapter.OnClickListener {
    private val searchableViewModel: SearchableViewModel by viewModel()
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_searchable, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureFloatingSearchView()
        configureRecyclerView()
        configureObservables()
        configureOnClick()
    }

    private fun configureFloatingSearchView() {
        floating_search_view.onQueryTextChange(this@SearchableFragment.lifecycle) {
            searchableViewModel.fetchItems(
                it
            )
        }

        floating_search_view.setOnHomeActionClickListener {
            val countBackStackEntry: Int? = parentFragmentManager.backStackEntryCount

            if (countBackStackEntry?.equals(0) ?: (false)) {
                activity?.onBackPressed()
            } else {
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun onClickRetry() {
        searchableViewModel.refreshFailedRequest()
    }

    override fun whenListIsUpdated(size: Int, networkState: NetworkState?) {
        updateUIWhenLoading(size, networkState)
        updateUIWhenEmptyList(size, networkState)
    }

    private fun configureOnClick() {
        emptyListButton.setOnClickListener { searchableViewModel.refreshAllList() }
    }

    private fun configureRecyclerView() {
        adapter = SearchAdapter(this)
        recyclerView.adapter = adapter
    }

    private fun configureObservables() {
        searchableViewModel.networkState?.observe(
            viewLifecycleOwner,
            Observer { adapter.updateNetworkState(it) })
        searchableViewModel.searchResults.observe(
            viewLifecycleOwner,
            Observer { adapter.submitList(it) })
    }

    private fun updateUIWhenEmptyList(size: Int, networkState: NetworkState?) {
        emptyListImage.visibility = View.GONE
        emptyListButton.visibility = View.GONE
        emptyListTitle.visibility = View.GONE
        if (size == 0) {
            when (networkState) {
                NetworkState.SUCCESS -> {
                    emptyListTitle.text = getString(R.string.no_result_found)
                    emptyListImage.visibility = View.VISIBLE
                    emptyListTitle.visibility = View.VISIBLE
                    emptyListButton.visibility = View.GONE
                }
                NetworkState.FAILED -> {
                    emptyListTitle.text = getString(R.string.technical_error)
                    emptyListImage.visibility = View.VISIBLE
                    emptyListTitle.visibility = View.VISIBLE
                    emptyListButton.visibility = View.VISIBLE
                }
                NetworkState.RUNNING -> {
                }
                null -> {
                }
            }
        }
    }

    private fun updateUIWhenLoading(size: Int, networkState: NetworkState?) {
        progressBar.visibility =
            if (size == 0 && networkState == NetworkState.RUNNING) View.VISIBLE else View.GONE
    }
}
