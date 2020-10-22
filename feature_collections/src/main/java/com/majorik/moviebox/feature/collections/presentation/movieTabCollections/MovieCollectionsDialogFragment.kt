package com.majorik.moviebox.feature.collections.presentation.movieTabCollections

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.majorik.library.base.extensions.setWindowTransparency
import com.majorik.library.base.extensions.updateMargin
import com.majorik.moviebox.feature.collections.R
import com.majorik.moviebox.feature.collections.databinding.DialogFragmentTabCollectionsBinding
import com.majorik.moviebox.domain.enums.collections.MovieCollectionType
import com.majorik.moviebox.feature.collections.presentation.adapters.FragmentsPagerAdapter
import kotlinx.android.synthetic.main.dialog_fragment_tab_collections.*
import com.majorik.moviebox.R as AppRes

class MovieCollectionsDialogFragment : DialogFragment(R.layout.dialog_fragment_tab_collections) {
    private val viewBinding: DialogFragmentTabCollectionsBinding by viewBinding()

    private val args: MovieCollectionsDialogFragmentArgs by navArgs()

    private lateinit var pagerAdapter: FragmentsPagerAdapter

    private val fragments = listOf<Fragment>(
        MovieCollectionsFragment(MovieCollectionType.NOW_PLAYING),
        MovieCollectionsFragment(MovieCollectionType.POPULAR),
        MovieCollectionsFragment(MovieCollectionType.TOP_RATED),
        MovieCollectionsFragment(MovieCollectionType.UPCOMING)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, AppRes.style.AppTheme_DialogFragmentTransparentStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWindowTransparency(::updateMargins)

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
                collection_view_pager.setCurrentItem(0, true)
            }

            MovieCollectionType.POPULAR -> {
                collection_view_pager.setCurrentItem(1, true)
            }

            MovieCollectionType.TOP_RATED -> {
                collection_view_pager.setCurrentItem(2, true)
            }

            MovieCollectionType.UPCOMING -> {
                collection_view_pager.setCurrentItem(3, true)
            }
        }
    }

    private fun updateMargins(statusBarSize: Int, @Suppress("UNUSED_PARAMETER") navigationBarSize: Int) {
        viewBinding.collectionsToolbar.updateMargin(top = statusBarSize)
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

        collection_view_pager.adapter = pagerAdapter
        collection_view_pager.offscreenPageLimit = fragments.size

        TabLayoutMediator(p_tab_layout, collection_view_pager) { tab, position ->
            tab.text = pagerTitles.getOrNull(position)
        }.attach()
    }
}
