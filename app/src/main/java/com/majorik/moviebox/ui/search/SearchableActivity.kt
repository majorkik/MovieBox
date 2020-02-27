package com.majorik.moviebox.ui.search

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.majorik.domain.NetworkState
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.SearchAdapter
import com.majorik.moviebox.adapters.search.SearchPagerAdapter
import com.majorik.moviebox.extensions.onQueryTextChange
import com.majorik.moviebox.extensions.setVisibilityOption
import com.majorik.moviebox.extensions.setWindowTransparency
import com.majorik.moviebox.extensions.updateMargin
import com.majorik.moviebox.ui.search.movie.SearchMovieFragment
import com.majorik.moviebox.ui.search.people.SearchPeopleFragment
import com.majorik.moviebox.ui.search.tv.SearchTVFragment
import kotlinx.android.synthetic.main.activity_searchable.*
import kotlinx.android.synthetic.main.activity_searchable.p_tab_layout
import kotlinx.android.synthetic.main.activity_searchable.p_view_pager
import org.koin.android.viewmodel.ext.android.viewModel


class SearchableActivity : AppCompatActivity() {

    private lateinit var searchPagerAdapter: SearchPagerAdapter

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

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.mine_shaft);
        }

        configureFloatingSearchView()
        configureTabLayout()
        configureClickListener()
    }

    private fun configureClickListener() {
        btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun updateMargins(statusBarSize: Int, @Suppress("UNUSED_PARAMETER") navigationBarSize: Int) {
        search_bar_layout.updateMargin(top = statusBarSize)
//        search_layout.updateMargin(bottom = navigationBarSize)
    }

    private fun configureTabLayout() {
        val pagerTitles: Array<String> =
            arrayOf(
                getString(R.string.movies),
                getString(R.string.tvs),
                getString(R.string.peoples)
            )

        searchPagerAdapter = SearchPagerAdapter(fragments, supportFragmentManager, lifecycle)
        p_view_pager.adapter = searchPagerAdapter
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
            item_view_type_toggle.setVisibilityOption(hasFocus)
        }

    }
}
