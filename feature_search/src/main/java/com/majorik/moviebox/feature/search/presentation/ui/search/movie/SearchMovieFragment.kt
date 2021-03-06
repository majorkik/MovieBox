package com.majorik.moviebox.feature.search.presentation.ui.search.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.moviebox.feature.search.presentation.ui.search.adapters.SearchMoviePagingAdapter
import com.majorik.moviebox.feature.search.presentation.ui.search.SearchQueryChangeListener
import com.majorik.moviebox.feature.search.presentation.ui.search.SearchViewTypeChangeListener
import com.majorik.library.base.extensions.setAdapterWithFixedSize
import com.majorik.library.base.extensions.px
import com.majorik.library.base.utils.SpacingDecoration
import com.majorik.moviebox.feature.search.R
import com.majorik.moviebox.feature.search.databinding.FragmentSearchPageBinding
import com.majorik.moviebox.feature.search.presentation.ui.search.SearchableFragmentDirections
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SearchMovieFragment :
    Fragment(R.layout.fragment_search_page),
    SearchQueryChangeListener,
    SearchViewTypeChangeListener {

    private val viewBinding: FragmentSearchPageBinding by viewBinding()

    private val viewModel: SearchMovieViewModel by viewModel()

    private var adapter: SearchMoviePagingAdapter = SearchMoviePagingAdapter { id ->
        findNavController().navigate(SearchableFragmentDirections.actionToMovieDetails(id))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        configureObservables()
    }

    override fun queryChange(query: String) {
        lifecycleScope.launchWhenResumed {
            adapter.submitData(PagingData.empty())
            viewModel.query.value = query
            viewModel.searchMoviesDataSource?.invalidate()
        }
    }

    private fun configureRecyclerView() {
        viewBinding.searchList.setAdapterWithFixedSize(ScaleInAnimationAdapter(adapter), true)
        viewBinding.searchList.addItemDecoration(SpacingDecoration(16.px(), 16.px(), true))
    }

    private fun configureObservables() {
        lifecycleScope.launchWhenResumed {
            viewModel.searchMoviesFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    override fun changeViewType(isGrid: Boolean) {
        if (isGrid) {
            viewBinding.searchList.layoutManager = GridLayoutManager(context, 3)
        } else {
            viewBinding.searchList.layoutManager = GridLayoutManager(context, 1)
        }

        adapter.setViewType(isGrid)
    }
}
