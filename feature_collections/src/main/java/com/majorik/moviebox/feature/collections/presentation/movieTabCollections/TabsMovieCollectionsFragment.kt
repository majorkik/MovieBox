package com.majorik.moviebox.feature.collections.presentation.movieTabCollections

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.majorik.moviebox.feature.collections.R
import com.majorik.moviebox.domain.enums.collections.MovieCollectionType
import com.majorik.moviebox.feature.collections.databinding.FragmentTabCollectionsBinding
import com.majorik.moviebox.feature.collections.presentation.adapters.FragmentsPagerAdapter

class TabsMovieCollectionsFragment : Fragment(R.layout.fragment_tab_collections) {
    private val viewBinding: FragmentTabCollectionsBinding by viewBinding()

    private val args: TabsMovieCollectionsFragmentArgs by navArgs()

    private lateinit var pagerAdapter: FragmentsPagerAdapter

    private val fragments = listOf<Fragment>(
        MovieCollectionsFragment(MovieCollectionType.NOW_PLAYING),
        MovieCollectionsFragment(MovieCollectionType.POPULAR),
        MovieCollectionsFragment(MovieCollectionType.TOP_RATED),
        MovieCollectionsFragment(MovieCollectionType.UPCOMING)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureTabLayout()
        setPage(args.movieCollectionType)
        setClickListener()
    }

    private fun setClickListener() {
        viewBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setPage(collectionName: MovieCollectionType) {
        when (collectionName) {
            MovieCollectionType.NOW_PLAYING -> {
                viewBinding.collectionViewPager.setCurrentItem(0, true)
            }

            MovieCollectionType.POPULAR -> {
                viewBinding.collectionViewPager.setCurrentItem(1, true)
            }

            MovieCollectionType.TOP_RATED -> {
                viewBinding.collectionViewPager.setCurrentItem(2, true)
            }

            MovieCollectionType.UPCOMING -> {
                viewBinding.collectionViewPager.setCurrentItem(3, true)
            }
        }
    }

    private fun configureTabLayout() {
        val pagerTitles: Array<String> =
            arrayOf(
                getString(R.string.collections_now_playing),
                getString(R.string.collections_collection_popular),
                getString(R.string.collections_collection_top_rated),
                getString(R.string.collections_upcoming)
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
