package com.majorik.moviebox.feature.search.presentation.ui.discover

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.majorik.library.base.base.BaseSlidingActivity
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.extensions.loadFragmentOrReturnNull
import com.majorik.library.base.extensions.setWindowTransparency
import com.majorik.library.base.extensions.updateMargin
import com.majorik.moviebox.feature.search.R
import com.majorik.moviebox.feature.search.databinding.ActivityDiscoverBinding
import kotlinx.android.synthetic.main.activity_discover.*
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel


class DiscoverActivity : BaseSlidingActivity() {
    private val viewBinding: ActivityDiscoverBinding by viewBinding(R.id.discover_container)

    private val viewModel: DiscoverViewModel by viewModel()

    override fun getRootView(): View = window.decorView.rootView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)

        setWindowTransparency(::updateMargins)
        setupActionBar()

        lifecycleScope.launchWhenResumed {
            delay(150)
            openFiltersDialog()
        }

        setClickListener()
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
    }

    private fun updateMargins(
        statusBarSize: Int,
        @Suppress("UNUSED_PARAMETER") navigationBarSize: Int
    ) {
        toolbar.updateMargin(top = statusBarSize)
    }

    private fun setClickListener() {
    }

    private fun openFiltersDialog() {
        val bottomSheetDialog =
            ScreenLinks.discoverFiltersDialog.loadFragmentOrReturnNull() as? BottomSheetDialogFragment
        bottomSheetDialog?.show(supportFragmentManager, "bottom_sheet_filters_dialog")
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_discover_arrow_down)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewBinding.toolbar.setNavigationOnClickListener {
            finish()
        }
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
