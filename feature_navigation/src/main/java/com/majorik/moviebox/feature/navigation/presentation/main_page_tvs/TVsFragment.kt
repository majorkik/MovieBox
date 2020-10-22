package com.majorik.moviebox.feature.navigation.presentation.main_page_tvs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.extensions.*
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.library.base.utils.GenresStorageObject
import com.majorik.moviebox.domain.enums.collections.TVCollectionType
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.data.repositories.TrendingRepository
import com.majorik.moviebox.feature.navigation.databinding.FragmentTvsBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.genre.GenreResponse
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TVResponse
import com.majorik.moviebox.feature.navigation.domain.youtubeModels.SearchResponse
import com.majorik.moviebox.feature.navigation.presentation.adapters.*
import com.majorik.moviebox.feature.navigation.presentation.adapters.tvs.NetworksAdapter
import com.majorik.moviebox.feature.navigation.presentation.constants.Networks
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TVsFragment : Fragment(R.layout.fragment_tvs) {
    private val viewBinding: FragmentTvsBinding by viewBinding()

    private val tvViewModel: TVsViewModel by viewModel()

    private var popularTVsAdapter: TVCardAdapter? = null
    private val airTodayAdapter = TVCollectionAdapter()
    private val onTheAirAdapter = TVCollectionAdapter()
    private val trailersAdapter = TrailersAdapter()
    private val genresAdapter = GenreAdapter()
    private val trendingTVsAdapter = TVTrendAdapter()
    private val networksAdapter = NetworksAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAdapters()
        fetchData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularTVsAdapter = TVCardAdapter(requireActivity())

        initAdapters()
        setObservers()
        setClickListeners()
    }

    private fun initAdapters() {
        lifecycleScope.launchWhenCreated {
            viewBinding.vpPopularTvs.setShowSideItems(16.px(), 16.px())
            viewBinding.vpPopularTvs.adapter = ScaleInAnimationAdapter(popularTVsAdapter)

            viewBinding.rvAirTodayTvs.setAdapterWithFixedSize(
                ScaleInAnimationAdapter(airTodayAdapter),
                true
            )

            viewBinding.rvOnTheAirTvs.setAdapterWithFixedSize(
                ScaleInAnimationAdapter(onTheAirAdapter),
                true
            )

            viewBinding.rvTrailers.setAdapterWithFixedSize(ScaleInAnimationAdapter(trailersAdapter), true)

            viewBinding.rvTvGenres.setAdapterWithFixedSize(genresAdapter, true)

            viewBinding.vpTrendTvs.adapter = trendingTVsAdapter

            viewBinding.networksList.adapter = networksAdapter
            networksAdapter.addItems(Networks.networks)
        }
    }

    private fun setupAdapters() {
        popularTVsAdapter?.setHasStableIds(true)
        airTodayAdapter.setHasStableIds(true)
        onTheAirAdapter.setHasStableIds(true)
        trailersAdapter.setHasStableIds(true)
        genresAdapter.setHasStableIds(true)
        trendingTVsAdapter.setHasStableIds(true)
        networksAdapter.setHasStableIds(true)
    }

    private fun setClickListeners() {
        viewBinding.btnSearch.setOnClickListener {
            startActivity(
                Intent().setClassName(
                    requireContext(),
                    ScreenLinks.searchableActivity
                )
            )
        }

        viewBinding.btnPopularTvs.setSafeOnClickListener {
            openNewActivityWithTab(
                ScreenLinks.tvCollection,
                TVCollectionType.POPULAR
            )
        }

        viewBinding.btnAirTodayTvs.setSafeOnClickListener {
            openNewActivityWithTab(
                ScreenLinks.tvCollection,
                TVCollectionType.AIRING_TODAY
            )
        }

        viewBinding.btnOnTheAirTvs.setSafeOnClickListener {
            openNewActivityWithTab(
                ScreenLinks.tvCollection,
                TVCollectionType.ON_THE_AIR
            )
        }

        viewBinding.btnTrendingTvs.setSafeOnClickListener {
        }
    }

    private fun fetchData() {
        tvViewModel.run {
            fetchPopularTVs(AppConfig.REGION, 1)

            fetchAirTodayTVs(AppConfig.REGION, 1)

            fetchTrendingTVs(TrendingRepository.TimeWindow.WEEK, 1, AppConfig.REGION)

            fetchOnTheAirTVs(AppConfig.REGION, 1)

            fetchTVGenres(AppConfig.REGION)

            searchYouTubeVideosByChannel()
        }
    }

    private fun setObservers() {
        tvViewModel.run {
            popularTVsLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    setPopularTVs(it)
                }
            )

            airTodayTVsLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    setAirTodayTVs(it)
                }
            )

            trendingTVsLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    setTrendingTVs(it)
                }
            )

            onTheAirTVsLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    setOnTheAirTVs(it)
                }
            )

            genresLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    setGenresAdapter(it)
                }
            )

            trailersLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    setTrailersAdapter(it)
                }
            )
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

    private fun setPopularTVs(result: ResultWrapper<TVResponse>) {
        when (result) {
            is ResultWrapper.GenericError -> {
            }

            is ResultWrapper.NetworkError -> {
            }

            is ResultWrapper.Success -> {
                popularTVsAdapter?.addItems(result.value.results)
            }
        }
    }

    private fun setAirTodayTVs(result: ResultWrapper<TVResponse>) {
        when (result) {
            is ResultWrapper.GenericError -> {
            }

            is ResultWrapper.NetworkError -> {
            }

            is ResultWrapper.Success -> {
                airTodayAdapter.addItems(result.value.results)
            }
        }
    }

    private fun setTrendingTVs(result: ResultWrapper<TVResponse>) {
        when (result) {
            is ResultWrapper.GenericError -> {
            }

            is ResultWrapper.NetworkError -> {
            }

            is ResultWrapper.Success -> {
                trendingTVsAdapter.addItems(result.value.results)
            }
        }
    }

    private fun setOnTheAirTVs(result: ResultWrapper<TVResponse>) {
        when (result) {
            is ResultWrapper.GenericError -> {
            }

            is ResultWrapper.NetworkError -> {
            }

            is ResultWrapper.Success -> {
                onTheAirAdapter.addItems(result.value.results)
            }
        }
    }

    private fun setGenresAdapter(result: ResultWrapper<GenreResponse>) {
        when (result) {
            is ResultWrapper.GenericError -> {
            }

            is ResultWrapper.NetworkError -> {
            }

            is ResultWrapper.Success -> {
                if (GenresStorageObject.tvGenres.isEmpty()) {
                    result.value.genres.map { genre ->
                        GenresStorageObject.tvGenres.put(
                            genre.id,
                            genre.name
                        )
                    }
                }

                genresAdapter.addItems(result.value.genres)
            }
        }
    }

    private fun setTrailersAdapter(result: ResultWrapper<SearchResponse>) {
        when (result) {
            is ResultWrapper.GenericError -> {
            }

            is ResultWrapper.NetworkError -> {
            }

            is ResultWrapper.Success -> {
                trailersAdapter.addItems(result.value.items)
            }
        }
    }
}
