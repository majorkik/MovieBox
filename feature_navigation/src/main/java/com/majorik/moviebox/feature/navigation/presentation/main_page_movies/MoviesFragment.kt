package com.majorik.moviebox.feature.navigation.presentation.main_page_movies

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.util.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.GenresConstants
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.enums.GenresType
import com.majorik.library.base.enums.SELECTED_GENRES_TYPE
import com.majorik.library.base.extensions.*
import com.majorik.library.base.utils.GenresStorageObject
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.data.repositories.TrendingRepository.TimeWindow
import com.majorik.moviebox.feature.navigation.databinding.FragmentMoviesBinding
import com.majorik.moviebox.feature.navigation.domain.movie.MovieCollectionType
import com.majorik.moviebox.feature.navigation.domain.movie.MovieCollectionType.*
import com.majorik.moviebox.feature.navigation.presentation.adapters.*
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.majorik.moviebox.R as AppResources

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val viewBinding: FragmentMoviesBinding by viewBinding()

    private val moviesViewModel: MoviesViewModel by viewModel()

    private val nowPlayingMoviesAdapter = MovieCollectionAdapter()
    private val upcomingMoviesAdapter = MovieDateCardAdapter()
    private val trailersAdapter = TrailersAdapter()
    private val peopleAdapter = PersonAdapter()
    private val popularMoviesAdapter = MovieCardAdapter()
    private val genresAdapter = GenreAdapter()
    private val trendingMovieAdapter = MovieTrendAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapters()
        setObservers()
        setClickListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(AppResources.menu.main_toolbar_menu, menu)
    }

    private fun initAdapters() {
        lifecycleScope.launchWhenCreated {
            //            popularMoviesAdapter.setHasStableIds(true)
            viewBinding.vpPopularMovies.setShowSideItems(16.toPx(), 16.toPx())
            viewBinding.vpPopularMovies.adapter = popularMoviesAdapter

//            nowPlayingMoviesAdapter.setHasStableIds(true)
            viewBinding.rvNowPlayingMovies.setAdapterWithFixedSize(
                ScaleInAnimationAdapter(nowPlayingMoviesAdapter),
                true
            )

//            upcomingMoviesAdapter.setHasStableIds(true)
            viewBinding.rvUpcomingMovies.setAdapterWithFixedSize(
                upcomingMoviesAdapter,
                true
            )

//            trailersAdapter.setHasStableIds(true)
            viewBinding.rvTrailers.setAdapterWithFixedSize(trailersAdapter, true)

//            peopleAdapter.setHasStableIds(true)
            viewBinding.rvTrendingPeoples.setAdapterWithFixedSize(
                peopleAdapter,
                false
            )

//            genresAdapter.setHasStableIds(true)
            viewBinding.rvMovieGenres.setAdapterWithFixedSize(genresAdapter, true)

//            trendingMovieAdapter.setHasStableIds(true)
            viewBinding.vpTrendMovies.adapter = trendingMovieAdapter
        }
    }

    private fun fetchData() {
        moviesViewModel.run {
            fetchPopularMovies(AppConfig.REGION, 1, "")

            fetchNowPlayingMovies(AppConfig.REGION, 1, "RU")

            fetchTrendingMovies(TimeWindow.WEEK, 1, AppConfig.REGION)

            fetchUpcomingMovies(AppConfig.REGION, 1, "RU")

            fetchMovieGenres(AppConfig.REGION)

            searchYouTubeVideosByChannel()

            fetchPopularPeoples(AppConfig.REGION, 1)
        }
    }

    private fun setClickListeners() {
        viewBinding.btnSearch.setOnClickListener {
            context?.startActivityWithAnim(ScreenLinks.searchableActivity)

//            parentFragmentManager.beginTransaction()
//                .setCustomAnimations(
//                    AppResources.anim.slide_in_up,
//                    AppResources.anim.slide_out_up,
//                    AppResources.anim.slide_exit_in_up,
//                    AppResources.anim.slide_exit_out_up
//                )
//                .add(
//                    AppResources.id.splash_container,
//                    "$PACKAGE_NAME.feature.navigation.presentation.main_page_tvs.TVsFragment".loadFragmentOrReturnNull()!!
//                )
//                .addToBackStack("tvs_fragment")
//                .commit()

//            findNavController().navigate(AppResources.id.nav_tvs)
        }

        viewBinding.btnPopularMovies.setOnClickListener { openNewActivityWithTab(ScreenLinks.movieCollection, POPULAR) }

        viewBinding.btnUpcomingMovies.setOnClickListener {
            openNewActivityWithTab(
                ScreenLinks.movieCollection,
                UPCOMING
            )
        }

        viewBinding.btnNowPlayingMovies.setOnClickListener {
            openNewActivityWithTab(
                ScreenLinks.movieCollection,
                NOW_PLAYING
            )
        }

        viewBinding.btnMovieGenres.setSafeOnClickListener {
            val intent = ScreenLinks.genresActivity.loadIntentOrReturnNull()

            intent?.putExtra(SELECTED_GENRES_TYPE, GenresType.MOVIE_GENRES)

            context?.startActivityWithAnim(intent)
        }
    }

    private fun setObservers() {
        moviesViewModel.run {
            popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
                popularMoviesAdapter.addItems(it)
            })

            nowPlayingMoviesLiveData.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launchWhenCreated {
                    delay(150)

                    nowPlayingMoviesAdapter.addItems(it)
                }
            })

            trendingMoviesLiveData.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launchWhenCreated {
                    if (GenresStorageObject.movieGenres.isEmpty()) {
                        GenresConstants.movieGenres.forEach {
                            GenresStorageObject.movieGenres.put(it.id, it.name)
                        }
                    }

                    delay(300)

                    trendingMovieAdapter.addItems(it)
                }
            })

            upcomingMoviesLiveData.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launch {
                    val items = it.sortedBy { it.releaseDate?.toDate()?.utc?.unixMillisLong ?: 0L }

                    delay(500)

                    upcomingMoviesAdapter.addItems(items)
                }
            })

            genresLiveData.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launchWhenCreated {
                    if (GenresStorageObject.movieGenres.isEmpty()) {
                        it.map { genre ->
                            GenresStorageObject.movieGenres.put(
                                genre.id,
                                genre.name
                            )
                        }
                    }

                    delay(700)

                    genresAdapter.addItems(it)
                }
            })

            trailersLiveData.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launchWhenCreated {
                    delay(900)

                    trailersAdapter.addItems(it)
                }
            })

            popularPeoplesLiveData.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launchWhenCreated {
                    delay(1100)

                    peopleAdapter.addItems(it)
                }
            })
        }
    }

    private fun openNewActivityWithTab(
        collectionsActivity: String,
        collectionType: MovieCollectionType
    ) {
        val intent = Intent().setClassName(requireContext(), collectionsActivity)

        intent.putExtra("collection_name", collectionType.name)

        startActivity(intent)
    }
}
