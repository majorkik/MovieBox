package com.majorik.moviebox.feature.details.presentation.movieDetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
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
import com.majorik.library.base.view.PullDownLayout
import com.majorik.moviebox.feature.details.R
import com.majorik.moviebox.feature.details.databinding.DialogFragmentMovieDetailsBinding
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.util.*
import kotlin.math.floor
import com.majorik.moviebox.R as AppRes

class MovieDetailsDialogFragment : DialogFragment(R.layout.dialog_fragment_movie_details), PullDownLayout.Callback {
    private val viewBinding: DialogFragmentMovieDetailsBinding by viewBinding()

    private val viewModel: MovieDetailsViewModel by viewModel()

    private val args: MovieDetailsDialogFragmentArgs by navArgs()

    private val stateObserver = Observer<MovieDetailsViewModel.ViewState> { state ->
        viewBinding.circularProgressBar.isVisible = state.isLoading
        viewBinding.linearProgressBar.isVisible = state.isLoading
        viewBinding.btnRefresh.isVisible = state.networkError

        if (state.details != null && state.isContentLoaded == true) {
            setMovieDetails(state.details)
        }

        state.isFavorite?.let {
            viewBinding.layoutMovieDetails.toggleFavorite.isChecked = it
        }

        state.isWatchlist?.let {
            viewBinding.layoutMovieDetails.toggleWatchlist.isChecked = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, AppRes.style.AppTheme_DialogFragmentTransparentStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.root.setCallback(this)

        updateMargins()

        viewModel.fetchMovieDetails(
            args.id,
            AppConfig.REGION,
            "images,credits,videos",
            "ru,en,null"
        )
        setClickListeners()

        observe(viewModel.stateLiveData, stateObserver)
    }

    private fun updateMargins() {
        viewBinding.root.doOnApplyWindowInsets { _, insets, _ ->
            lifecycleScope.launch {
                viewBinding.bottomBar.updateMargin(bottom = insets.systemWindowInsetBottom)
                viewBinding.contentCoordinator.updateMargin(top = insets.systemWindowInsetTop)
                viewBinding.loadingStubLayout.updateMargin(top = 220.px() - insets.systemWindowInsetTop)

                viewBinding.root.awaitNextLayout()

                val height = viewBinding.root.height
                val peekHeight = height - viewBinding.mdImageSlider.height

                val contentBottomSheet = BottomSheetBehavior.from(viewBinding.layoutMovieDetails.root)
                contentBottomSheet.setPeekHeight(peekHeight, false)
            }

            insets
        }
    }

    private fun setClickListeners() {
        viewBinding.layoutMovieDetails.toggleFavorite.setOnClickListener {
            viewModel.markMovieIsFavorite(viewBinding.layoutMovieDetails.toggleFavorite.isChecked)
        }

        viewBinding.layoutMovieDetails.toggleWatchlist.setOnClickListener {
            viewModel.addMovieToWatchlist(viewBinding.layoutMovieDetails.toggleWatchlist.isChecked)
        }

        viewBinding.btnExtraMenu.setSafeOnClickListener {
            openExtraMenuDialog()
        }

        viewBinding.bottomBar.setSafeOnClickListener {
            openWatchOnlineDialog()
        }

        viewBinding.run {
            btnRefresh.setSafeOnClickListener {
                viewModel.fetchMovieDetails(
                    args.id,
                    AppConfig.REGION,
                    "images,credits,videos",
                    "ru,en,null"
                )
            }
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
        viewBinding.layoutMovieDetails.mBackdropImage.setOnClickListener {
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

        viewBinding.layoutMovieDetails.mPosterImage.setOnClickListener {
            StfalconImageViewer.Builder(requireContext(), images.posters.map { it.filePath }) { view, image ->
                view.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_1280 + image)
            }.withHiddenStatusBar(false).show()
        }
    }

    private fun setTrailerButtonClickListener(videos: Videos) {
        if (videos.results.isNotEmpty()) {
            viewBinding.layoutMovieDetails.btnWatchTrailer.setOnClickListener {
                context?.openYouTube(videos.results[0].key)
            }
        }
    }

    /**
     * Details
     */

    private fun setMovieDetails(movie: MovieDetails) {
        viewBinding.layoutMovieDetails.root.isVisible = true
        viewBinding.btnRefresh.isVisible = false
        viewBinding.bottomBar.isVisible = true
        viewBinding.loadingStubLayout.isVisible = false

        setHeader(movie.title, movie.voteAverage, movie.status, movie.genres, movie.releaseDate)
        setOverview(movie.overview)
        setFacts(movie)
        setImages(movie.images, movie.backdropPath, movie.posterPath)
        setPeoples(movie.credits.casts)
        setTrailerButtonClickListener(movie.videos)
    }

    private fun setPeoples(casts: List<Cast>) {
        viewBinding.layoutMovieDetails.mPersons.setAdapterWithFixedSize(CastAdapter(::openPersonDetails, casts), true)
    }

    private fun setImages(images: Images, backdropPath: String?, posterPath: String?) {
        viewBinding.layoutMovieDetails.mBackdropImage.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_780 + backdropPath)
        viewBinding.layoutMovieDetails.mPosterImage.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_500 + posterPath)
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
        viewBinding.layoutMovieDetails.apply {
            mTitle.text = title
            mVoteAverage.text = voteAverage.toString()
            mStatus.text = context?.combineString(getString(R.string.details_status), status)
            voteAverageIndicator.setIndicatorColor(voteAverage)
        }
        setGenres(genres, releaseDate)
    }

    private fun setCompanies(productionCompanies: List<ProductionCompany>) {
        viewBinding.layoutMovieDetails.mCompanies.text = viewBinding.root.context.combineString(
            getString(R.string.details_company_production),
            productionCompanies.joinToString(", ") { it.name }
        )
    }

    private fun setOriginalTitle(originalTitle: String) {
        viewBinding.layoutMovieDetails.mOriginalTitle.text =
            viewBinding.root.context.combineString(getString(R.string.details_original_title), originalTitle)
    }

    private fun setRevenue(revenue: Long) {
        val numFormat = DecimalFormat("#,###,###")

        viewBinding.layoutMovieDetails.mRevenue.text =
            viewBinding.root.context.combineString(
                getString(R.string.details_revenue),
                (numFormat.format(revenue) + " $")
            )
    }

    private fun setBudget(budget: Int) {
        val numFormat = DecimalFormat("#,###,###")

        viewBinding.layoutMovieDetails.mBudget.text = viewBinding.root.context.combineString(
            getString(R.string.details_budget),
            (numFormat.format(budget) + " $")
        )
    }

    private fun setReleaseDate(releaseDate: String) {
        viewBinding.layoutMovieDetails.mReleaseDate.text =
            viewBinding.root.context.combineString(
                getString(R.string.details_release_date),
                KlockLocale.default.formatDateLong.format(releaseDate.toDate(getString(R.string.details_yyyy_mm_dd)))
            )
    }

    private fun setOriginalLanguage(originalLanguage: String) {
        viewBinding.layoutMovieDetails.mOriginalLanguage.text =
            viewBinding.root.context.combineString(
                getString(R.string.details_original_language),
                Locale(originalLanguage).displayLanguage.capitalize(AppConfig.APP_LOCALE)
            )
    }

    private fun setGenres(genres: List<Genre>, releaseDate: String) {
        val genresFormat = genres.joinToString(", ") { it.name.capitalize(AppConfig.APP_LOCALE) }

        viewBinding.layoutMovieDetails.mAddInfo.text = getString(
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

            viewBinding.layoutMovieDetails.mRuntime.text = viewBinding.root.context.combineString(
                getString(R.string.details_runtime),
                getString(
                    R.string.details_runtime_mask,
                    stringHours,
                    stringMinutes
                )
            )
        } else {
            viewBinding.layoutMovieDetails.mRuntime.text = context?.combineString(
                getString(R.string.details_runtime),
                getString(R.string.details_unknown)
            )
        }
    }

    private fun setOverview(overview: String?) {
        if (overview.isNullOrBlank()) {
            viewBinding.layoutMovieDetails.mOverviewEmpty.setVisibilityOption(true)
            viewBinding.layoutMovieDetails.mOverview.setVisibilityOption(false)
        } else {
            viewBinding.layoutMovieDetails.mOverviewEmpty.setVisibilityOption(false)
            viewBinding.layoutMovieDetails.mOverview.setVisibilityOption(true)
            viewBinding.layoutMovieDetails.mOverview.text = overview
        }
    }

    private fun setImageSlider(images: List<String>) {
        viewBinding.mdImageSlider.adapter = ImageSliderAdapter(images)
    }

    /**
     * Actions
     */

    private fun openPersonDetails(id: Int) {
        findNavController().navigate(MovieDetailsDialogFragmentDirections.actionToPersonDetails(id))
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
        return BottomSheetBehavior.from(viewBinding.layoutMovieDetails.root).state == BottomSheetBehavior.STATE_COLLAPSED
    }
}
