package com.majorik.moviebox.feature.search.presentation.ui.discover

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.extensions.px
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.utils.SpacingDecoration
import com.majorik.moviebox.feature.search.R
import com.majorik.moviebox.feature.search.databinding.FragmentDiscoverBinding
import com.majorik.moviebox.feature.search.domain.enums.DiscoverType
import com.majorik.moviebox.feature.search.domain.models.discover.DiscoverFiltersModel
import com.majorik.moviebox.feature.search.presentation.ui.discover.discover.DiscoverAdapter
import com.majorik.moviebox.feature.search.presentation.ui.filters.DiscoverFiltersBottomSheetFragment
import com.majorik.moviebox.feature.search.presentation.ui.filters.DiscoverFiltersBottomSheetFragment.Companion.KEY_DISCOVER_FILTERS_REQUEST
import com.majorik.moviebox.feature.search.presentation.ui.filters.DiscoverFiltersBottomSheetFragment.Companion.KEY_FILTERS_MODEL
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscoverFragment : Fragment(R.layout.fragment_discover) {
    private val viewBinding: FragmentDiscoverBinding by viewBinding()

    private val viewModel: DiscoverViewModel by viewModel()

    private var discoverAdapter: DiscoverAdapter? = null

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        var anim: Animation? = super.onCreateAnimation(transit, enter, nextAnim)

        if (anim == null && nextAnim != 0) {
            anim = AnimationUtils.loadAnimation(context, nextAnim)
        }

        anim?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) = Unit

            override fun onAnimationEnd(animation: Animation?) {
                openFiltersDialogByDefault()
            }

            override fun onAnimationRepeat(animation: Animation?) = Unit
        })

        return anim
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListener()
        configAdapters()
        setObservers()
        setFiltersResultListener()

        viewBinding.toggleItemViewType.setOnCheckedChangeListener { buttonView, isChecked ->
            discoverAdapter?.setViewType(!isChecked)
        }

        viewBinding.btnFilters.setSafeOnClickListener {
            openFiltersDialog()
        }

        viewBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun openFiltersDialogByDefault() {
        lifecycleScope.launchWhenResumed {
            delay(150)
            openFiltersDialog()
        }
    }

    private fun setClickListener() {
    }

    private fun setObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.discoverFlow.collectLatest { pagingData ->
                discoverAdapter?.submitData(pagingData)
            }
        }
    }

    private fun configAdapters() {
        discoverAdapter = DiscoverAdapter(
            { isGrid ->
                val spanCount = if (isGrid) 3 else 1

                viewBinding.discoverList.layoutManager = GridLayoutManager(context, spanCount)
            },
            { id, type ->
                if (type == DiscoverType.MOVIES) {
                    findNavController().navigate(DiscoverFragmentDirections.actionToMovieDetails(id))
                } else {
                    findNavController().navigate(DiscoverFragmentDirections.actionToTvDetails(id))
                }
            }
        )

        viewBinding.discoverList.setHasFixedSize(true)
        viewBinding.discoverList.adapter = discoverAdapter
        viewBinding.discoverList.addItemDecoration(SpacingDecoration(16.px(), 16.px(), true))
    }

    private fun openFiltersDialog() {
        val bottomSheetDialog = DiscoverFiltersBottomSheetFragment.newInstance(viewModel.filtersModel)
        bottomSheetDialog.show(childFragmentManager, "bottom_sheet_filters_dialog")
    }

    private fun setFiltersResultListener() {
        childFragmentManager.setFragmentResultListener(
            KEY_DISCOVER_FILTERS_REQUEST,
            this,
            { _: String, result: Bundle ->
                (result.getSerializable(KEY_FILTERS_MODEL) as? DiscoverFiltersModel)?.let {
                    viewModel.filtersModel = it
                }
                discoverAdapter?.refresh()
            }
        )
    }
}
