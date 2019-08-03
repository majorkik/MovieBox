package com.majorik.moviebox.ui.homepage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.MovieCollectionAdapter
import com.majorik.moviebox.adapters.TVCollectionAdapter
import com.majorik.moviebox.extensions.setAdapterWithFixedSize
import com.majorik.moviebox.ui.base.BaseNavigationFragment
import com.majorik.moviebox.ui.movieTabCollections.MovieCollectionsActivity
import com.majorik.moviebox.ui.tvTabCollections.TVCollectionsActivity
import kotlinx.android.synthetic.main.fragment_homepage.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseNavigationFragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun getLayoutId() = R.layout.fragment_homepage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData()
        setObservers()
        setClickListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_toolbar_menu, menu)
    }

    override fun fetchData() {
        homeViewModel.fetchPopularMovies("ru-RU", 1, "")
        homeViewModel.fetchPopularTVs("ru-RU", 1)
        homeViewModel.fetchUpcomingMovies("ru-RU", 1, "")
        homeViewModel.fetchAiringTodayTVs("ru-RU", 1)
    }

    private fun setClickListeners() {
        btn_more_popular_m.setOnClickListener {
            openNewActivityWithTab(MovieCollectionsActivity::class.java, 0)
        }
        btn_more_popular_tv.setOnClickListener {
            openNewActivityWithTab(TVCollectionsActivity::class.java, 0)
        }
        btn_more_upcoming_m.setOnClickListener {
            openNewActivityWithTab(MovieCollectionsActivity::class.java, 1)
        }
        btn_more_on_air_tv.setOnClickListener {
            openNewActivityWithTab(TVCollectionsActivity::class.java, 1)
        }
    }

    override fun setObservers() {
        homeViewModel.popularMoviesLiveData.observe(this, Observer {
            home_list_popular_m.setAdapterWithFixedSize(MovieCollectionAdapter(it), true)
        })

        homeViewModel.popularTVsLiveData.observe(this, Observer {
            home_list_popular_tv.setAdapterWithFixedSize(TVCollectionAdapter(it), true)
        })

        homeViewModel.upcomingMoviesLiveData.observe(this, Observer {
            home_list_upcoming_m.setAdapterWithFixedSize(MovieCollectionAdapter(it), true)
        })

        homeViewModel.airingTodayTVLiveData.observe(this, Observer {
            home_list_airing_today.setAdapterWithFixedSize(TVCollectionAdapter(it), true)
        })
    }

    private fun openNewActivityWithTab(collectionsActivity: Class<*>, numTab: Int) {
        val intent = Intent(context, collectionsActivity)

        intent.putExtra("page", numTab)

        startActivity(intent)
    }
}