package com.majorik.moviebox.feature.search.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.majorik.moviebox.feature.search.presentation.adapters.FragmentsPagerAdapter
import com.majorik.moviebox.feature.search.presentation.ui.movie.SearchMovieFragment
import com.majorik.moviebox.feature.search.presentation.ui.people.SearchPeopleFragment
import com.majorik.moviebox.feature.search.presentation.ui.tv.SearchTVFragment
import com.majorik.library.base.extensions.onQueryTextChange
import com.majorik.library.base.extensions.setVisibilityOption
import com.majorik.library.base.extensions.setWindowTransparency
import com.majorik.library.base.extensions.updateMargin
import kotlinx.android.synthetic.main.activity_searchable.*
import com.majorik.moviebox.R as AppResources
import com.majorik.moviebox.feature.search.R

class SearchableActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: FragmentsPagerAdapter

    private val fragments =
        listOf<Fragment>(
            SearchMovieFragment(),
            SearchTVFragment(),
            SearchPeopleFragment()
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)

        setWindowTransparency(::updateMargins)

        window.navigationBarColor = ContextCompat.getColor(this, AppResources.color.mine_shaft)

        configureFloatingSearchView()
        configureTabLayout()
        configureClickListener()
    }

    private fun configureClickListener() {
        btn_back.setOnClickListener {
            onBackPressed()
        }

        toggle_is_grid_type.setOnCheckedChangeListener { buttonView, isChecked ->
            pagerAdapter.changeViewType(isChecked)
        }
    }

    private fun updateMargins(
        statusBarSize: Int,
        @Suppress("UNUSED_PARAMETER") navigationBarSize: Int
    ) {
        search_bar_layout.updateMargin(top = statusBarSize)
//        search_layout.updateMargin(bottom = navigationBarSize)
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
            supportFragmentManager,
            lifecycle
        )
        p_view_pager.adapter = pagerAdapter
        p_view_pager.offscreenPageLimit = 3

        TabLayoutMediator(p_tab_layout, p_view_pager) { tab, position ->
            tab.text = pagerTitles.getOrNull(position)
        }.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun configureFloatingSearchView() {
        searchView.requestFocus()

        searchView.onQueryTextChange(lifecycle) { query ->
            //            searchableViewModel.fetchItems(it)
            fragments.forEach {
                if (it is SearchQueryChangeListener) {
                    it.queryChange(query)
                }
            }
        }

        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            toggle_is_grid_type.setVisibilityOption(hasFocus)
        }
    }
}
