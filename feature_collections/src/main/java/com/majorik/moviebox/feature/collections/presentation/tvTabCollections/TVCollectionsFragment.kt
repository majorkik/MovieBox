package com.majorik.moviebox.feature.collections.presentation.tvTabCollections

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.extensions.px
import com.majorik.library.base.utils.SpacingDecoration
import com.majorik.moviebox.domain.enums.collections.TVCollectionType
import com.majorik.moviebox.feature.collections.R
import com.majorik.moviebox.feature.collections.databinding.FragmentCollectionPageBinding
import com.majorik.moviebox.feature.collections.presentation.tvTabCollections.adapters.PagingTVCollectionAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TVCollectionsFragment(tvCollectionType: TVCollectionType) : Fragment(R.layout.fragment_collection_page) {

    private val viewBinding: FragmentCollectionPageBinding by viewBinding()

    private val viewModel: TVCollectionsViewModel by viewModel { parametersOf(tvCollectionType) }

    private val adapter: PagingTVCollectionAdapter = PagingTVCollectionAdapter(::openTVDetails)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        setObservers()
    }

    private fun configureRecyclerView() {
        viewBinding.gridItems.addItemDecoration(SpacingDecoration(16.px(), 16.px(), true))
        viewBinding.gridItems.adapter = ScaleInAnimationAdapter(adapter)
    }

    private fun setObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.tvsFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    /**
     * Actions
     */

    private fun openTVDetails(id: Int) {
        findNavController().navigate(TabsTVCollectionsFragmentDirections.actionToTvDetails(id))
    }
}
