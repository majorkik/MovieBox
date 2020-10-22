package com.majorik.moviebox.feature.collections.presentation.tvTabCollections

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.majorik.library.base.extensions.setWindowTransparency
import com.majorik.library.base.extensions.updateMargin
import com.majorik.moviebox.feature.collections.R
import com.majorik.moviebox.feature.collections.databinding.DialogFragmentTabCollectionsBinding
import com.majorik.moviebox.domain.enums.collections.TVCollectionType
import com.majorik.moviebox.feature.collections.presentation.adapters.FragmentsPagerAdapter
import kotlinx.android.synthetic.main.dialog_fragment_tab_collections.*

class TVCollectionsDialogFragment : DialogFragment(R.layout.dialog_fragment_tab_collections) {
    private val viewBinding: DialogFragmentTabCollectionsBinding by viewBinding()

    private val args: TVCollectionsDialogFragmentArgs by navArgs()

    private lateinit var pagerAdapter: FragmentsPagerAdapter

    private val fragments = listOf<Fragment>(
        TVCollectionsFragment(TVCollectionType.POPULAR),
        TVCollectionsFragment(TVCollectionType.AIRING_TODAY),
        TVCollectionsFragment(TVCollectionType.ON_THE_AIR),
        TVCollectionsFragment(TVCollectionType.TOP_RATED)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWindowTransparency(::updateMargins)

        configureTabLayout()
        setPage(args.tvCollectionType)
    }

    private fun setPage(collectionName: TVCollectionType) {
        when (collectionName) {
            TVCollectionType.POPULAR -> {
                collection_view_pager.setCurrentItem(0, true)
            }

            TVCollectionType.AIRING_TODAY -> {
                collection_view_pager.setCurrentItem(1, true)
            }

            TVCollectionType.ON_THE_AIR -> {
                collection_view_pager.setCurrentItem(2, true)
            }

            TVCollectionType.TOP_RATED -> {
                collection_view_pager.setCurrentItem(3, true)
            }
        }
    }

    private fun updateMargins(
        statusBarSize: Int,
        @Suppress("UNUSED_PARAMETER") navigationBarSize: Int
    ) {
        viewBinding.collectionsToolbar.updateMargin(top = statusBarSize)
    }

    private fun configureTabLayout() {
        val pagerTitles: Array<String> =
            arrayOf(
                getString(R.string.collections_collection_popular),
                getString(R.string.collections_air_today),
                getString(R.string.collections_on_the_air),
                getString(R.string.collections_collection_top_rated)
            )

        pagerAdapter = FragmentsPagerAdapter(
            fragments,
            childFragmentManager,
            lifecycle
        )

        collection_view_pager.adapter = pagerAdapter
        collection_view_pager.offscreenPageLimit = fragments.size

        TabLayoutMediator(p_tab_layout, collection_view_pager) { tab, position ->
            tab.text = pagerTitles.getOrNull(position)
        }.attach()
    }
}
