package com.majorik.moviebox.feature.search.presentation.ui.discover

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.base.BaseSlidingActivity
import com.majorik.library.base.extensions.setWindowTransparency
import com.majorik.library.base.extensions.px
import com.majorik.library.base.extensions.updateMargin
import com.majorik.library.base.utils.SpacingDecoration
import com.majorik.moviebox.feature.search.R
import com.majorik.moviebox.feature.search.databinding.ActivityDiscoverBinding
import com.majorik.moviebox.feature.search.domain.models.discover.DiscoverFiltersModel
import com.majorik.moviebox.feature.search.presentation.ui.discover.discover.DiscoverAdapter
import com.majorik.moviebox.feature.search.presentation.ui.filters.DiscoverFiltersBottomSheetFragment
import com.majorik.moviebox.feature.search.presentation.ui.filters.DiscoverFiltersBottomSheetFragment.Companion.KEY_DISCOVER_FILTERS_REQUEST
import com.majorik.moviebox.feature.search.presentation.ui.filters.DiscoverFiltersBottomSheetFragment.Companion.KEY_FILTERS_MODEL
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscoverActivity : BaseSlidingActivity() {
    private val viewBinding: ActivityDiscoverBinding by viewBinding(R.id.discover_container)

    private val viewModel: DiscoverViewModel by viewModel()

    private val discoverAdapter: DiscoverAdapter = DiscoverAdapter() { isGrid ->
        val spanCount = if (isGrid) 3 else 1

        viewBinding.discoverList.layoutManager = GridLayoutManager(this, spanCount)
    }

    override fun getRootView(): View = window.decorView.rootView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)

        setWindowTransparency(::updateMargins)
        setupActionBar()

        openFiltersDialogByDefault()

        setClickListener()
        configAdapters()
        setObservers()
        setFiltersResultListener()
    }

    private fun openFiltersDialogByDefault() {
        lifecycleScope.launchWhenResumed {
            delay(150)
            openFiltersDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_discover_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_type_selector -> {
                changeMenuItemIcon(item)
            }

            R.id.menu_filters -> {
                openFiltersDialog()
            }
        }

        return true
    }

    private fun changeMenuItemIcon(item: MenuItem) {
        val isChecked = !item.isChecked

        if (item.isChecked) {
            item.icon = ContextCompat.getDrawable(this, R.drawable.ic_search_module)
        } else {
            item.icon = ContextCompat.getDrawable(this, R.drawable.ic_list_agenda)
        }

        item.isChecked = isChecked

        discoverAdapter.setViewType(!isChecked)
    }

    private fun updateMargins(
        statusBarSize: Int,
        @Suppress("UNUSED_PARAMETER") navigationBarSize: Int
    ) {
        viewBinding.toolbar.updateMargin(top = statusBarSize)
    }

    private fun setClickListener() {
    }

    private fun setObservers() {
        viewModel.apply {
            lifecycleScope.launchWhenResumed {
                discoverFlow.collectLatest { pagingData ->
                    discoverAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun configAdapters() {
        viewBinding.discoverList.setHasFixedSize(true)
        viewBinding.discoverList.adapter = discoverAdapter
        viewBinding.discoverList.addItemDecoration(SpacingDecoration(16.px(), 16.px(), true))
    }

    private fun openFiltersDialog() {
        val bottomSheetDialog = DiscoverFiltersBottomSheetFragment.newInstance(viewModel.filtersModel)
        bottomSheetDialog.show(supportFragmentManager, "bottom_sheet_filters_dialog")
    }

    private fun setupActionBar() {
        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_discover_arrow_down)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewBinding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setFiltersResultListener() {
        supportFragmentManager.setFragmentResultListener(
            KEY_DISCOVER_FILTERS_REQUEST,
            this,
            FragmentResultListener { requestKey: String, result: Bundle ->
                (result.getSerializable(KEY_FILTERS_MODEL) as? DiscoverFiltersModel)?.let {
                    viewModel.filtersModel = it
                }
                discoverAdapter.refresh()
            }
        )
    }

    /**
     * Sliding methods
     */

    override fun onSlidingStarted() {
    }

    override fun onSlidingFinished() {
    }

    override fun canSlideDown(): Boolean = false
}
