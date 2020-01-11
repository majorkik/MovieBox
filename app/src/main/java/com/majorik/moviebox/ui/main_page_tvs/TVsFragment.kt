package com.majorik.moviebox.ui.main_page_tvs

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.TVCardAdapter
import com.majorik.moviebox.adapters.TVCollectionAdapter
import com.majorik.moviebox.extensions.setAdapterWithFixedSize
import com.majorik.moviebox.ui.base.BaseNavigationFragment
import com.majorik.moviebox.ui.search.SearchableActivity
import com.majorik.moviebox.ui.tvTabCollections.TVCollectionsActivity
import kotlinx.android.synthetic.main.fragment_tvs.*
import kotlinx.android.synthetic.main.fragment_tvs.btn_search
import org.koin.android.viewmodel.ext.android.viewModel

class TVsFragment : BaseNavigationFragment() {
    private val tvViewModel: TVsViewModel by viewModel()

    override fun getLayoutId() = R.layout.fragment_tvs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData()
        setClickListeners()
        setObservers()
    }

    private fun setClickListeners() {
        btn_search.setOnClickListener {
            startActivity(Intent(context, SearchableActivity::class.java))
        }

        btn_popular_tvs.setOnClickListener {
            openNewActivityWithTab(TVCollectionsActivity::class.java, 0)
        }

        btn_air_today_tvs.setOnClickListener {
            openNewActivityWithTab(TVCollectionsActivity::class.java, 1)
        }

        btn_on_the_air_tvs.setOnClickListener {
            openNewActivityWithTab(TVCollectionsActivity::class.java, 2)
        }
    }

    override fun fetchData() {
        tvViewModel.fetchPopularTVs("ru", 1)
        tvViewModel.fetchAirTodayTVs("ru", 1)
        tvViewModel.fetchOnTheAirTVs("ru", 1)
    }

    override fun setObservers() {
        tvViewModel.popularTVsLiveData.observe(viewLifecycleOwner, Observer {
            vp_popular_tvs.run {
                adapter = TVCardAdapter(it)
                pageMargin = ((16 * resources.displayMetrics.density).toInt())
            }
        })

        tvViewModel.airTodayTVsLiveData.observe(viewLifecycleOwner, Observer {
            rv_air_today_tvs.setAdapterWithFixedSize(TVCollectionAdapter(it), true)
        })

        tvViewModel.onTheAirTVsLiveData.observe(viewLifecycleOwner, Observer {
            rv_on_the_air_tvs.setAdapterWithFixedSize(TVCollectionAdapter(it), true)
        })
    }

    private fun openNewActivityWithTab(collectionsActivity: Class<*>, numTab: Int) {
        val intent = Intent(context, collectionsActivity)

        intent.putExtra("page", numTab)

        startActivity(intent)
    }
}
