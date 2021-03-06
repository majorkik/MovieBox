package com.majorik.moviebox.feature.collections.presentation.tvTabCollections

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.majorik.library.base.extensions.updateMargin
import com.majorik.moviebox.feature.collections.R
import com.majorik.moviebox.domain.enums.collections.TVCollectionType
import com.majorik.moviebox.feature.collections.databinding.FragmentTabCollectionsBinding
import com.majorik.moviebox.feature.collections.presentation.adapters.FragmentsPagerAdapter

class TabsTVCollectionsFragment : Fragment(R.layout.fragment_tab_collections) {
    private val viewBinding: FragmentTabCollectionsBinding by viewBinding()

    private val args: TabsTVCollectionsFragmentArgs by navArgs()

    private lateinit var pagerAdapter: FragmentsPagerAdapter

    private val fragments = listOf<Fragment>(
        TVCollectionsFragment(TVCollectionType.POPULAR),
        TVCollectionsFragment(TVCollectionType.AIRING_TODAY),
        TVCollectionsFragment(TVCollectionType.ON_THE_AIR),
        TVCollectionsFragment(TVCollectionType.TOP_RATED)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureTabLayout()
        setPage(args.tvCollectionType)
        setClickListener()
    }

    private fun setClickListener() {
        viewBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setPage(collectionName: TVCollectionType) {
        when (collectionName) {
            TVCollectionType.POPULAR -> {
                viewBinding.collectionViewPager.setCurrentItem(0, true)
            }

            TVCollectionType.AIRING_TODAY -> {
                viewBinding.collectionViewPager.setCurrentItem(1, true)
            }

            TVCollectionType.ON_THE_AIR -> {
                viewBinding.collectionViewPager.setCurrentItem(2, true)
            }

            TVCollectionType.TOP_RATED -> {
                viewBinding.collectionViewPager.setCurrentItem(3, true)
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

        viewBinding.collectionViewPager.adapter = pagerAdapter
        viewBinding.collectionViewPager.offscreenPageLimit = fragments.size

        TabLayoutMediator(viewBinding.pTabLayout, viewBinding.collectionViewPager) { tab, position ->
            tab.text = pagerTitles.getOrNull(position)
        }.attach()
    }
}
