package com.majorik.moviebox.feature.navigation.presentation.main_page_tvs

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.util.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.majorik.moviebox.feature.navigation.data.repositories.TrendingRepository
import com.majorik.moviebox.feature.navigation.domain.movie.TVCollectionType
import com.majorik.moviebox.feature.navigation.presentation.adapters.*
import com.majorik.library.base.extensions.*
import com.majorik.library.base.base.BaseNavigationFragment
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.utils.GenresStorageObject
import com.majorik.library.base.utils.PACKAGE_NAME
import com.majorik.moviebox.feature.navigation.presentation.adapters.TrailersAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_tvs.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TVsFragment : BaseNavigationFragment() {
    private val tvViewModel: TVsViewModel by viewModel()

    private lateinit var popularTVsAdapter: TVCardAdapter
    private val airTodayAdapter = TVCollectionAdapter()
    private val onTheAirAdapter = TVCollectionAdapter()
    private val trailersAdapter = TrailersAdapter()
    private val genresAdapter = GenreAdapter()
    private val trendingTVsAdapter = TVTrendAdapter()

    override fun getLayoutId() = com.majorik.moviebox.feature.navigation.R.layout.fragment_tvs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularTVsAdapter = TVCardAdapter(requireActivity())

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
            startActivity(
                Intent().setClassName(
                    requireContext(),
                    "$PACKAGE_NAME.feature.search.presentation.ui.SearchableActivity"
                )
            )
        }

        btn_popular_tvs.setSafeOnClickListener {
            openNewActivityWithTab(
                "$PACKAGE_NAME.feature.collections.presentation.tvTabCollections.TVCollectionsActivity",
                TVCollectionType.POPULAR
            )
        }

        btn_air_today_tvs.setSafeOnClickListener {
            openNewActivityWithTab(
                "$PACKAGE_NAME.feature.collections.presentation.tvTabCollections.TVCollectionsActivity",
                TVCollectionType.AIRING_TODAY
            )
        }

        btn_on_the_air_tvs.setSafeOnClickListener {
            openNewActivityWithTab(
                "$PACKAGE_NAME.feature.collections.presentation.tvTabCollections.TVCollectionsActivity",
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
        collectionsActivity: String,
        collectionType: TVCollectionType
    ) {
        val intent = collectionsActivity.loadIntentOrReturnNull()

        intent?.putExtra("collection_name", collectionType.name)

        startActivity(intent)
    }

    companion object {
        fun newInstance() = TVsFragment()
    }
}
