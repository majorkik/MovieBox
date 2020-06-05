package com.majorik.moviebox.feature.navigation.presentation.main_page_tvs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.util.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.majorik.library.base.constants.AppConfig
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.extensions.*
import com.majorik.library.base.utils.GenresStorageObject
import com.majorik.library.base.utils.PACKAGE_NAME
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.data.repositories.TrendingRepository
import com.majorik.moviebox.feature.navigation.databinding.FragmentTvsBinding
import com.majorik.moviebox.feature.navigation.domain.movie.TVCollectionType
import com.majorik.moviebox.feature.navigation.presentation.adapters.*
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TVsFragment : Fragment(R.layout.fragment_tvs) {

    private val viewBinding: FragmentTvsBinding by viewBinding()

    private val tvViewModel: TVsViewModel by viewModel()

    private lateinit var popularTVsAdapter: TVCardAdapter
    private val airTodayAdapter = TVCollectionAdapter()
    private val onTheAirAdapter = TVCollectionAdapter()
    private val trailersAdapter = TrailersAdapter()
    private val genresAdapter = GenreAdapter()
    private val trendingTVsAdapter = TVTrendAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchData()

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            parentFragmentManager.popBackStack()
        }
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
            viewBinding.vpPopularTvs.setShowSideItems(16.toPx(), 16.toPx())
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
        }
    }

    private fun setClickListeners() {
        viewBinding.btnSearch.setOnClickListener {
            startActivity(
                Intent().setClassName(
                    requireContext(),
                    "$PACKAGE_NAME.feature.search.presentation.ui.SearchableActivity"
                )
            )
        }

        viewBinding.btnPopularTvs.setSafeOnClickListener {
            openNewActivityWithTab(
                "$PACKAGE_NAME.feature.collections.presentation.tvTabCollections.TVCollectionsActivity",
                TVCollectionType.POPULAR
            )
        }

        viewBinding.btnAirTodayTvs.setSafeOnClickListener {
            openNewActivityWithTab(
                "$PACKAGE_NAME.feature.collections.presentation.tvTabCollections.TVCollectionsActivity",
                TVCollectionType.AIRING_TODAY
            )
        }

        viewBinding.btnOnTheAirTvs.setSafeOnClickListener {
            openNewActivityWithTab(
                "$PACKAGE_NAME.feature.collections.presentation.tvTabCollections.TVCollectionsActivity",
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
}
