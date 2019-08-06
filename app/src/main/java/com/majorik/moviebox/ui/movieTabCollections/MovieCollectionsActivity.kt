package com.majorik.moviebox.ui.movieTabCollections

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.majorik.domain.tmdbModels.movie.MovieCollectionType
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.TabCollectionsAdapter
import com.majorik.moviebox.ui.base.BaseTabActivity
import kotlinx.android.synthetic.main.activity_tab_collections.*

class MovieCollectionsActivity : BaseTabActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureTabLayout(intent.extras?.getInt("page") ?: 0)
    }

    override fun getLayoutId() = R.layout.activity_tab_collections

    override fun getToolbarTitle() = "Фильмы"

    private fun configureTabLayout(page: Int) {
        val adapter = TabCollectionsAdapter(supportFragmentManager)

        adapter.addFragment(
            MovieCollectionsFragment(MovieCollectionType.NOW_PLAYING),
            "Сейчас идут"
        )
        adapter.addFragment(MovieCollectionsFragment(MovieCollectionType.UPCOMING), "Ожидаемые")
        adapter.addFragment(MovieCollectionsFragment(MovieCollectionType.POPULAR), "Популярные")
        adapter.addFragment(
            MovieCollectionsFragment(MovieCollectionType.TOP_RATED),
            "Самые популярные"
        )


        view_pager.adapter = adapter

        if (adapter.count < 3) {
            tab_layout.tabMode = TabLayout.MODE_FIXED
        } else {
            tab_layout.tabMode = TabLayout.MODE_SCROLLABLE
        }

        tab_layout.setupWithViewPager(view_pager)
        tab_layout.getTabAt(page)?.select()
    }

}
