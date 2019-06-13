package com.majorik.moviebox.ui.tv

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.CollectionAdapter
import com.majorik.moviebox.ui.base.BaseNavigationFragment
import kotlinx.android.synthetic.main.fragment_tv.*
import org.koin.android.viewmodel.ext.android.viewModel

class TVFragment : BaseNavigationFragment() {
    private val tvViewModel: TVViewModel by viewModel()

    override fun getLayoutId() = R.layout.fragment_tv

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData()
        setObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_toolbar_menu, menu)
    }

    override fun fetchData() {
        tvViewModel.fetchPopularTVs("", 1)
        tvViewModel.fetchTopRatedTVs("", 1)
    }

    override fun setObservers() {
        tvViewModel.popularTVsLiveData.observe(this, Observer {
            list_popular_tvs.adapter = CollectionAdapter(it)
        })

        tvViewModel.topRatedTVsLiveData.observe(this, Observer {
            list_top_rated_tvs.adapter = CollectionAdapter(it)
        })
    }
}
