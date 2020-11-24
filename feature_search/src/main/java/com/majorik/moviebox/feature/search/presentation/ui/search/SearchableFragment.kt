package com.majorik.moviebox.feature.search.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.majorik.library.base.extensions.onQueryTextChange
import com.majorik.library.base.extensions.setVisibilityOption
import com.majorik.moviebox.feature.search.presentation.adapters.FragmentsPagerAdapter
import com.majorik.moviebox.feature.search.presentation.ui.search.movie.SearchMovieFragment
import com.majorik.moviebox.feature.search.presentation.ui.search.people.SearchPeopleFragment
import com.majorik.moviebox.feature.search.presentation.ui.search.tv.SearchTVFragment
import com.majorik.moviebox.feature.search.R
import com.majorik.moviebox.feature.search.databinding.FragmentSearchableBinding

class SearchableFragment : Fragment(R.layout.fragment_searchable) {

    private val viewBinding: FragmentSearchableBinding by viewBinding()

    private var pagerAdapter: FragmentsPagerAdapter? = null

    private val fragments =
        listOf<Fragment>(
            SearchMovieFragment(),
            SearchTVFragment(),
            SearchPeopleFragment()
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureFloatingSearchView()
        configureTabLayout()
        setClickListener()
    }

    private fun setClickListener() {
        viewBinding.apply {
            viewBinding.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            toggleIsGridType.setOnCheckedChangeListener { buttonView, isChecked ->
                pagerAdapter?.changeViewType(isChecked)
            }
        }
    }

    private fun configureTabLayout() {
        val pagerTitles: Array<String> =
            arrayOf(
                getString(R.string.search_movies),
                getString(R.string.search_tvs),
                getString(R.string.search_peoples)
            )

        pagerAdapter = FragmentsPagerAdapter(
            fragments,
            childFragmentManager,
            lifecycle
        )
        viewBinding.pViewPager.adapter = pagerAdapter
        viewBinding.pViewPager.offscreenPageLimit = 3

        TabLayoutMediator(viewBinding.pTabLayout, viewBinding.pViewPager) { tab, position ->
            tab.text = pagerTitles.getOrNull(position)
        }.attach()
    }

    private fun configureFloatingSearchView() {
        viewBinding.searchView.requestFocus()

        viewBinding.searchView.onQueryTextChange(lifecycle) { query ->
            fragments.forEach {
                if (it is SearchQueryChangeListener) {
                    it.queryChange(query)
                }
            }
        }

        viewBinding.searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            viewBinding.toggleIsGridType.setVisibilityOption(hasFocus)
        }
    }
}
