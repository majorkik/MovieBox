package com.majorik.moviebox.ui.tv

import android.graphics.Point
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.MovieCollectionSliderAdapter
import com.majorik.moviebox.adapters.TVCollectionAdapter
import com.majorik.moviebox.adapters.TVCollectionSliderAdapter
import com.majorik.moviebox.extensions.CardsPagerTransformerShift
import com.majorik.moviebox.extensions.setAdapterWithFixedSize
import com.majorik.moviebox.ui.base.BaseNavigationFragment
import kotlinx.android.synthetic.main.fragment_movie.*
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
        tvViewModel.fetchAiringTodayTVs("ru", 1)
        tvViewModel.fetchOnTheAirTVs("", 1)
    }

    override fun setObservers() {
        tvViewModel.popularTVsLiveData.observe(this, Observer {
            list_popular_tvs.setAdapterWithFixedSize(TVCollectionAdapter(it), true)
        })

        tvViewModel.topRatedTVsLiveData.observe(this, Observer {
            list_top_rated_tvs.setAdapterWithFixedSize(TVCollectionAdapter(it), true)
        })

        tvViewModel.airingTodayTVsLiveData.observe(this, Observer {
            airing_today_tvs.adapter = TVCollectionSliderAdapter(it)
            val screen = Point()
            activity?.windowManager?.defaultDisplay?.getSize(screen)
            val startOffset = (32.0 / (screen.x - 2.0 * 32.0))
            airing_today_tvs.pageMargin = ((16 * resources.displayMetrics.density).toInt())
            airing_today_tvs.setPageTransformer(
                false,
                CardsPagerTransformerShift(24, 8, 0.75F, startOffset.toFloat())
            )
        })

        tvViewModel.onTheAirTVsLiveData.observe(this, Observer {
            list_on_the_air_tvs.setAdapterWithFixedSize(TVCollectionAdapter(it), true)
        })
    }
}
