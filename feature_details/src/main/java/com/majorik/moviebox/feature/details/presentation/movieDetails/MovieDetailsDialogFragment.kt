package com.majorik.moviebox.feature.details.presentation.movieDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.*
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.library.base.view.PullDownLayout
import com.majorik.moviebox.feature.details.R
import com.majorik.moviebox.feature.details.databinding.DialogFragmentMovieDetailsBinding
import com.majorik.moviebox.feature.details.domain.tmdbModels.account.AccountStates
import com.majorik.moviebox.feature.details.domain.tmdbModels.cast.Cast
import com.majorik.moviebox.feature.details.domain.tmdbModels.genre.Genre
import com.majorik.moviebox.feature.details.domain.tmdbModels.image.Images
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.MovieDetails
import com.majorik.moviebox.feature.details.domain.tmdbModels.production.ProductionCompany
import com.majorik.moviebox.feature.details.domain.tmdbModels.video.Videos
import com.majorik.moviebox.feature.details.presentation.adapters.CastAdapter
import com.majorik.moviebox.feature.details.presentation.adapters.ImageSliderAdapter
import com.majorik.moviebox.feature.details.presentation.watch_online.WatchOnlineDialog
import com.soywiz.klock.KlockLocale
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.dialog_fragment_movie_details.*
import kotlinx.android.synthetic.main.layout_movie_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.util.*
import kotlin.math.floor
import com.majorik.moviebox.R as AppRes

class MovieDetailsDialogFragment : DialogFragment(R.layout.dialog_fragment_movie_details), PullDownLayout.Callback {
    private val viewBinding: DialogFragmentMovieDetailsBinding by viewBinding()

    private val viewModel: MovieDetailsViewModel by viewModel()

    private val args: MovieDetailsDialogFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, AppRes.style.AppTheme_DialogFragmentTransparentStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.root.setCallback(this)

        setWindowTransparency(::updateMargins)

        fetchDetails()
        fetchMovieStates()
        setClickListeners()
        setObserver()
    }

    private fun updateMargins(statusBarSize: Int, navigationBarSize: Int) {
        viewBinding.bottomBar.updateMargin(bottom = navigationBarSize)
        viewBinding.contentCoordinator.updateMargin(top = statusBarSize)

        val height = viewBinding.root.height
        val peekHeight = height - viewBinding.mdImageSlider.height

        val contentBottomSheet = BottomSheetBehavior.from(md_bottom_sheet)
        contentBottomSheet.setPeekHeight(peekHeight, false)
    }

    private fun fetchDetails() {
        viewModel.fetchMovieDetails(
            args.id,
            AppConfig.REGION,
            "images,credits,videos",
            "ru,en,null"
        )
    }

    private fun fetchMovieStates() {
        lifecycleScope.launchWhenResumed {
            when (val result = viewModel.fetchAccountStateForMovie(args.id)) {
                is ResultWrapper.Success -> {
                    viewModel.movieState = result.value
                }
            }
        }
    }

    private fun setObserver() {
        viewModel.movieDetailsLiveData.observe(
            this,
            Observer { movie ->
                setMovieDetails(movie)
            }
        )

        viewModel.movieStatesLiveData.observe(
            this,
            Observer {
                it?.apply { setAccountStates(this) }
            }
        )

        viewModel.responseFavoriteLiveData.observe(
            this,
            Observer {
                if (it.statusCode == 1 || it.statusCode == 12 || it.statusCode == 13) {
                    context?.showToastMessage("Фильм успешно добавлен в избранное")
                } else {
                    context?.showToastMessage("Неудалось добавить фильм в избранное")
                }
            }
        )

        viewModel.responseWatchlistLiveData.observe(
            this,
            Observer {
                if (it.statusCode == 1 || it.statusCode == 12 || it.statusCode == 13) {
                    context?.showToastMessage("Фильм успешно добавлен в 'Буду смотреть'")
                } else {
                    context?.showToastMessage("Неудалось добавить фильм в 'Буду смотреть'")
                }
            }
        )
    }

    private fun setAccountStates(accountStates: AccountStates) {
        toggle_favorite.isChecked = accountStates.favorite
        toggle_watchlist.isChecked = accountStates.watchlist
    }

    private fun setClickListeners() {
        toggle_favorite.setOnClickListener {
            viewModel.movieState?.let {
                viewModel.markMovieIsFavorite(it.id, !it.favorite)
            }
        }

        toggle_watchlist.setOnClickListener {
            viewModel.movieState?.let {
                viewModel.addMovieToWatchlist(it.id, !it.watchlist)
            }
        }

        btn_extra_menu.setSafeOnClickListener {
            openExtraMenuDialog()
        }

        bottom_bar.setSafeOnClickListener {
            openWatchOnlineDialog()
        }
    }

    private fun openWatchOnlineDialog() {
        val watchOnlineDialog = WatchOnlineDialog()
        watchOnlineDialog.show(childFragmentManager, "watch_online_dialog")
    }

    private fun openExtraMenuDialog() {
        val extraMenuBottomDialog = MovieExtraMenuBottomDialog()
        extraMenuBottomDialog.show(childFragmentManager, "extra_menu_dialog")
    }

    private fun setClickListenerForImages(images: Images) {
        m_backdrop_image.setOnClickListener {
            StfalconImageViewer.Builder(requireContext(), images.backdrops.map { it.filePath }) { view, image ->
                view.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_1280 + image)
            }.withOverlayView(
                layoutInflater.inflate(
                    R.layout.item_genre_inline_details,
                    null
                )
            )
                .withHiddenStatusBar(false)
                .show()
        }

        m_poster_image.setOnClickListener {
            StfalconImageViewer.Builder(requireContext(), images.posters.map { it.filePath }) { view, image ->
                view.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_1280 + image)
            }.withHiddenStatusBar(false).show()
        }
    }

    private fun setTrailerButtonClickListener(videos: Videos) {
        if (videos.results.isNotEmpty()) {
            btn_watch_trailer.setOnClickListener {
                context?.openYouTube(videos.results[0].key)
            }
        }
    }

    /**
     * Details
     */

    private fun setMovieDetails(movie: MovieDetails) {
        setHeader(movie.title, movie.voteAverage, movie.status, movie.genres, movie.releaseDate)
        setOverview(movie.overview)
        setFacts(movie)
        setImages(movie.images, movie.backdropPath, movie.posterPath)
        setPeoples(movie.credits.casts)
        setTrailerButtonClickListener(movie.videos)
    }

    private fun setPeoples(casts: List<Cast>) {
        m_persons.setAdapterWithFixedSize(CastAdapter(casts), true)
    }

    private fun setImages(images: Images, backdropPath: String?, posterPath: String?) {
        m_backdrop_image.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_780 + backdropPath)
        m_poster_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_500 + posterPath)
        setImageSlider(images.backdrops.map { imageInfo -> imageInfo.filePath }.take(6))
        setClickListenerForImages(images)
    }

    private fun setFacts(movie: MovieDetails?) {
        if (movie == null) return

        setOriginalLanguage(movie.originalLanguage)
        setOriginalTitle(movie.originalTitle)
        setRevenue(movie.revenue)
        setBudget(movie.budget)
        setReleaseDate(movie.releaseDate)
        setRuntime(movie.runtime)
        setCompanies(movie.productionCompanies)
    }

    private fun setHeader(
        title: String,
        voteAverage: Double,
        status: String,
        genres: List<Genre>,
        releaseDate: String
    ) {
        m_title.text = title
        m_vote_average.text = voteAverage.toString()
        m_status.text = context?.combineString(getString(R.string.details_status), status)
        vote_average_indicator.setIndicatorColor(voteAverage)

        setGenres(genres, releaseDate)
    }

    private fun setCompanies(productionCompanies: List<ProductionCompany>) {
        m_companies.text = viewBinding.root.context.combineString(
            getString(R.string.details_company_production),
            productionCompanies.joinToString(", ") { it.name }
        )
    }

    private fun setOriginalTitle(originalTitle: String) {
        m_original_title.text =
            viewBinding.root.context.combineString(getString(R.string.details_original_title), originalTitle)
    }

    private fun setRevenue(revenue: Long) {
        val numFormat = DecimalFormat("#,###,###")

        m_revenue.text =
            viewBinding.root.context.combineString(
                getString(R.string.details_revenue),
                (numFormat.format(revenue) + " $")
            )
    }

    private fun setBudget(budget: Int) {
        val numFormat = DecimalFormat("#,###,###")

        m_budget.text = viewBinding.root.context.combineString(
            getString(R.string.details_budget),
            (numFormat.format(budget) + " $")
        )
    }

    private fun setReleaseDate(releaseDate: String) {
        m_release_date.text =
            viewBinding.root.context.combineString(
                getString(R.string.details_release_date),
                KlockLocale.default.formatDateLong.format(releaseDate.toDate(getString(R.string.details_yyyy_mm_dd)))
            )
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun setOriginalLanguage(originalLanguage: String) {
        m_original_language.text =
            viewBinding.root.context.combineString(
                getString(R.string.details_original_language),
                Locale(originalLanguage).displayLanguage.capitalize(AppConfig.APP_LOCALE)
            )
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun setGenres(genres: List<Genre>, releaseDate: String) {
        val genresFormat = genres.joinToString(", ") { it.name.capitalize(AppConfig.APP_LOCALE) }

        m_add_info.text = getString(
            R.string.details_short_info_mask,
            releaseDate.toDate(getString(R.string.details_yyyy_mm_dd)).yearInt.toString(),
            genresFormat
        )
    }

    private fun setRuntime(runtime: Int?) {
        if (runtime != null) {
            val hours = floor(runtime / 60.0).toInt()
            val stringHours =
                resources.getQuantityString(R.plurals.hours, hours, hours)

            val minutes = runtime % 60
            val stringMinutes =
                resources.getQuantityString(R.plurals.minutes, minutes, minutes)

            m_runtime.text = viewBinding.root.context.combineString(
                getString(R.string.details_runtime),
                getString(
                    R.string.details_runtime_mask,
                    stringHours,
                    stringMinutes
                )
            )
        } else {
            m_runtime.text = context?.combineString(
                getString(R.string.details_runtime),
                getString(R.string.details_unknown)
            )
        }
    }

    private fun setOverview(overview: String?) {
        if (overview.isNullOrBlank()) {
            m_overview_empty.setVisibilityOption(true)
            m_overview.setVisibilityOption(false)
        } else {
            m_overview_empty.setVisibilityOption(false)
            m_overview.setVisibilityOption(true)
            m_overview.text = overview
        }
    }

    private fun setImageSlider(images: List<String>) {
        md_image_slider.adapter = ImageSliderAdapter(images)
    }

    /**
     * PullDownLayout methods
     */

    override fun onPullStart() = Unit

    override fun onPull(progress: Float) = Unit

    override fun onPullCancel() = Unit

    override fun onPullComplete() {
        findNavController().popBackStack()
    }

    override fun pullDownReady(): Boolean {
        return BottomSheetBehavior.from(md_bottom_sheet).state == BottomSheetBehavior.STATE_COLLAPSED
    }
}
