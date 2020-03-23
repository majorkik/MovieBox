package com.majorik.moviebox.ui.main_page_tvs

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.util.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.majorik.data.repositories.TrendingRepository
import com.majorik.domain.constants.AppConfig
import com.majorik.domain.enums.movie.MovieCollectionType
import com.majorik.domain.enums.movie.TVCollectionType
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.GenreAdapter
import com.majorik.moviebox.adapters.TrailersAdapter
import com.majorik.moviebox.adapters.tv.TVCardAdapter
import com.majorik.moviebox.adapters.tv.TVCollectionAdapter
import com.majorik.moviebox.adapters.tv.TVTrendAdapter
import com.majorik.moviebox.extensions.setAdapterWithFixedSize
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.setShowSideItems
import com.majorik.moviebox.extensions.toPx
import com.majorik.moviebox.ui.base.BaseNavigationFragment
import com.majorik.moviebox.ui.movieTabCollections.MovieCollectionsActivity
import com.majorik.moviebox.ui.search.SearchableActivity
import com.majorik.moviebox.ui.tvTabCollections.TVCollectionsActivity
import com.majorik.moviebox.utils.GenresStorageObject
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_tvs.*
import org.koin.android.viewmodel.ext.android.viewModel

class TVsFragment : BaseNavigationFragment() {
    private val tvViewModel: TVsViewModel by viewModel()

    private val popularTVsAdapter = TVCardAdapter()
    private val airTodayAdapter = TVCollectionAdapter()
    private val onTheAirAdapter = TVCollectionAdapter()
    private val trailersAdapter = TrailersAdapter()
    private val genresAdapter = GenreAdapter()
    private val trendingTVsAdapter = TVTrendAdapter()

    override fun getLayoutId() = R.layout.fragment_tvs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapters()
        fetchData()
        setObservers()
        setClickListeners()
    }

    private fun initAdapters() {
        lifecycleScope.launchWhenCreated {
            popularTVsAdapter.setHasStableIds(true)
            vp_popular_tvs.setShowSideItems(16.toPx(), 16.toPx())
            vp_popular_tvs.adapter = ScaleInAnimationAdapter(popularTVsAdapter)

            airTodayAdapter.setHasStableIds(true)
            rv_air_today_tvs.setAdapterWithFixedSize(
                ScaleInAnimationAdapter(airTodayAdapter),
                true
            )

            onTheAirAdapter.setHasStableIds(true)
            rv_on_the_air_tvs.setAdapterWithFixedSize(
                ScaleInAnimationAdapter(onTheAirAdapter),
                true
            )

            trailersAdapter.setHasStableIds(true)
            rv_trailers.setAdapterWithFixedSize(ScaleInAnimationAdapter(trailersAdapter), true)

            genresAdapter.setHasStableIds(true)
            rv_tv_genres.setAdapterWithFixedSize(genresAdapter, true)

            trendingTVsAdapter.setHasStableIds(true)
            vp_trend_tvs.adapter = trendingTVsAdapter
        }
    }

    private fun setClickListeners() {
        btn_search.setOnClickListener {
            startActivity(Intent(context, SearchableActivity::class.java))
        }

        btn_popular_tvs.setSafeOnClickListener {
            openNewActivityWithTab(
                TVCollectionsActivity::class.java,
                TVCollectionType.POPULAR
            )
        }

        btn_air_today_tvs.setSafeOnClickListener {
            openNewActivityWithTab(
                TVCollectionsActivity::class.java,
                TVCollectionType.AIRING_TODAY
            )
        }

        btn_on_the_air_tvs.setSafeOnClickListener {
            openNewActivityWithTab(
                TVCollectionsActivity::class.java,
                TVCollectionType.ON_THE_AIR
            )
        }

        btn_trending_tvs.setSafeOnClickListener {

        }
    }

    override fun fetchData() {
        tvViewModel.run {
            fetchPopularTVs(AppConfig.REGION, 1)

            fetchAirTodayTVs(AppConfig.REGION, 1)

            fetchTrendingTVs(TrendingRepository.TimeWindow.WEEK, 1, AppConfig.REGION)

            fetchOnTheAirTVs(AppConfig.REGION, 1)

            fetchTVGenres(AppConfig.REGION)

            searchYouTubeVideosByChannel()
        }
    }

    override fun setObservers() {
        tvViewModel.run {
            popularTVsLiveData.observe(viewLifecycleOwner, Observer {
                popularTVsAdapter.addItems(it)
            })

            airTodayTVsLiveData.observe(viewLifecycleOwner, Observer {
                airTodayAdapter.addItems(it)
            })

            trendingTVsLiveData.observe(viewLifecycleOwner, Observer {
                trendingTVsAdapter.addItems(it)
            })

            onTheAirTVsLiveData.observe(viewLifecycleOwner, Observer {
                onTheAirAdapter.addItems(it)
            })

            genresLiveData.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launchWhenCreated {
                    if (GenresStorageObject.tvGenres.isEmpty()) {
                        it.map { genre ->
                            GenresStorageObject.movieGenres.put(
                                genre.id,
                                genre.name
                            )
                        }
                    }

                    genresAdapter.addItems(it)
                }
            })

            trailersLiveData.observe(viewLifecycleOwner, Observer {
                trailersAdapter.addItems(it)
            })
        }
    }

    private fun openNewActivityWithTab(
        collectionsActivity: Class<*>,
        collectionType: TVCollectionType
    ) {
        val intent = Intent(context, collectionsActivity)

        intent.putExtra("collection_name", collectionType.name)

        startActivity(intent)
    }

    companion object {
        fun newInstance() = TVsFragment()
    }
}
