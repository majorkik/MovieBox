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
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.extensions.*
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.library.base.utils.GenresStorageObject
import com.majorik.moviebox.domain.enums.collections.TVCollectionType
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.databinding.FragmentTvsBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.genre.GenreResponse
import com.majorik.moviebox.feature.navigation.domain.youtubeModels.SearchResponse
import com.majorik.moviebox.feature.navigation.presentation.adapters.*
import com.majorik.moviebox.feature.navigation.domain.config.Networks
import com.majorik.moviebox.feature.navigation.presentation.main_page_tvs.adapters.NetworksAdapter
import com.majorik.moviebox.feature.navigation.presentation.main_page_tvs.adapters.TVCardAdapter
import com.majorik.moviebox.feature.navigation.presentation.main_page_tvs.adapters.TVCollectionAdapter
import com.majorik.moviebox.feature.navigation.presentation.main_page_tvs.adapters.TVTrendAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class TVsFragment : Fragment(R.layout.fragment_tvs) {
    private val viewBinding: FragmentTvsBinding by viewBinding()

    private val viewModel: TVsViewModel by viewModel()

    private val popularTVsAdapter = TVCardAdapter(::actionClickTV)
    private val airTodayAdapter = TVCollectionAdapter(::actionClickTV)
    private val onTheAirAdapter = TVCollectionAdapter(::actionClickTV)
    private val trendingTVsAdapter = TVTrendAdapter(::actionClickTV)

    private val trailersAdapter = TrailersAdapter()
    private val genresAdapter = GenreAdapter()
    private val networksAdapter = NetworksAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    private fun setClickListeners() {
        viewBinding.btnSearch.setOnClickListener {
        }

        viewBinding.btnPopularTvs.setSafeOnClickListener {
            findNavController().navigate(TVsFragmentDirections.actionToTvCollections(TVCollectionType.POPULAR))
        }

        viewBinding.btnAirTodayTvs.setSafeOnClickListener {
            findNavController().navigate(TVsFragmentDirections.actionToTvCollections(TVCollectionType.AIRING_TODAY))
        }

        viewBinding.btnOnTheAirTvs.setSafeOnClickListener {
            findNavController().navigate(TVsFragmentDirections.actionToTvCollections(TVCollectionType.ON_THE_AIR))
        }

        viewBinding.btnTrendingTvs.setSafeOnClickListener {
            findNavController().navigate(TVsFragmentDirections.actionToTvCollections(TVCollectionType.TOP_RATED))
        }
    }

    private fun setObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.trendingTVsFlow.collectLatest { pagingAdapter ->
                trendingTVsAdapter.submitData(pagingAdapter)
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.popularTVsFlow.collectLatest { pagingAdapter ->
                popularTVsAdapter.submitData(pagingAdapter)
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.onTheAirTVsFlow.collectLatest { pagingAdapter ->
                onTheAirAdapter.submitData(pagingAdapter)
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.airingTodayTVsFlow.collectLatest { pagingAdapter ->
                airTodayAdapter.submitData(pagingAdapter)
            }
        }

        viewModel.run {
            genresLiveData.observe(viewLifecycleOwner, Observer { setGenresAdapter(it) })

            trailersLiveData.observe(viewLifecycleOwner, Observer { setTrailersAdapter(it) })
        }
    }

    private fun setGenresAdapter(result: ResultWrapper<GenreResponse>) {
        when (result) {
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
            is ResultWrapper.Success -> {
                trailersAdapter.addItems(result.value.items)
            }
        }
    }

    /**
     * Actions
     */

    private fun actionClickTV(id: Int) {
        findNavController().navigate(TVsFragmentDirections.actionToTvDetails(id))
    }
}
