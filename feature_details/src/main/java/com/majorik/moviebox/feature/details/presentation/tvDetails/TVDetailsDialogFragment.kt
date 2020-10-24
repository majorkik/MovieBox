package com.majorik.moviebox.feature.details.presentation.tvDetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.*
import com.majorik.library.base.view.PullDownLayout
import com.majorik.moviebox.feature.details.R
import com.majorik.moviebox.feature.details.databinding.DialogFragmentTvDetailsBinding
import com.majorik.moviebox.feature.details.domain.tmdbModels.account.AccountStates
import com.majorik.moviebox.feature.details.domain.tmdbModels.cast.Cast
import com.majorik.moviebox.feature.details.domain.tmdbModels.genre.Genre
import com.majorik.moviebox.feature.details.domain.tmdbModels.image.Images
import com.majorik.moviebox.feature.details.domain.tmdbModels.production.ProductionCompany
import com.majorik.moviebox.feature.details.domain.tmdbModels.tv.TVDetails
import com.majorik.moviebox.feature.details.domain.tmdbModels.video.Videos
import com.majorik.moviebox.feature.details.presentation.adapters.CastAdapter
import com.majorik.moviebox.feature.details.presentation.adapters.ImageSliderAdapter
import com.majorik.moviebox.feature.details.presentation.watch_online.WatchOnlineDialog
import com.soywiz.klock.KlockLocale
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.dialog_fragment_tv_details.*
import kotlinx.android.synthetic.main.layout_tv_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TVDetailsDialogFragment : DialogFragment(R.layout.dialog_fragment_tv_details), PullDownLayout.Callback {
    private val viewBinding: DialogFragmentTvDetailsBinding by viewBinding()

    private val tvDetailsViewModel: TVDetailsViewModel by viewModel()

    private val args: TVDetailsDialogFragmentArgs by navArgs()

    private var tvState: AccountStates? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, com.majorik.moviebox.R.style.AppTheme_DialogFragmentTransparentStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.root.setCallback(this)

        setWindowTransparency(::updateMargins)

        fetchDetails()
        setClickListeners()
        setObserver()
    }

    private fun updateMargins(statusBarSize: Int, navigationBarSize: Int) {
        viewBinding.bottomBar.updateMargin(bottom = navigationBarSize)
        viewBinding.contentCoordinator.updateMargin(top = statusBarSize)

        val height = viewBinding.root.height
        val peekHeight = height - viewBinding.tdImageSlider.height

        val contentBottomSheet = BottomSheetBehavior.from(td_bottom_sheet)
        contentBottomSheet.setPeekHeight(peekHeight, false)
    }

    private fun fetchDetails() {
        tvDetailsViewModel.fetchTVDetails(
            args.id,
            AppConfig.REGION,
            "images,credits,videos",
            "ru,en,null"
        )

        tvDetailsViewModel.fetchAccountStateForTV(args.id)
    }

    private fun setClickListeners() {
        toggle_favorite.setOnClickListener {
            tvState?.let {
                tvDetailsViewModel.markTVIsFavorite(
                    it.id,
                    !it.favorite
                )
            }
        }

        toggle_watchlist.setOnClickListener {
            tvState?.let {
                tvDetailsViewModel.addTVToWatchlist(
                    it.id,
                    !it.watchlist
                )
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
        val extraMenuBottomDialog = TVExtraMenuBottomDialog()
        extraMenuBottomDialog.show(childFragmentManager, "extra_menu_dialog")
    }

    private fun setClickListenerForImages(
        images: Images
    ) {
        t_backdrop_image.setOnClickListener {
            StfalconImageViewer.Builder(context, images.backdrops.map { it.filePath }) { view, image ->
                view.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_1280 + image)
            }.withHiddenStatusBar(false).show()
        }

        t_poster_image.setOnClickListener {
            StfalconImageViewer.Builder(context, images.posters.map { it.filePath }) { view, image ->
                view.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_1280 + image)
            }.withHiddenStatusBar(false).show()
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun setObserver() {
        tvDetailsViewModel.tvDetailsLiveData.observe(
            this,
            Observer { tv ->
                setTVDetails(tv)
            }
        )

        tvDetailsViewModel.tvStatesLiveData.observe(
            this,
            Observer {
                it?.apply { setAccountState(this) }
            }
        )

        tvDetailsViewModel.responseFavoriteLiveData.observe(
            this,
            Observer {
                if (it.statusCode == 1 || it.statusCode == 12 || it.statusCode == 13) {
                    Toast.makeText(context, "Сериал успешно добавлен в избранное", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(context, "Неудалось добавить сериал в избранное", Toast.LENGTH_LONG)
                        .show()
                }
            }
        )

        tvDetailsViewModel.responseWatchlistLiveData.observe(
            this,
            Observer {
                if (it.statusCode == 1 || it.statusCode == 12 || it.statusCode == 13) {
                    Toast.makeText(
                        context,
                        "Сериал успешно добавлен в 'Буду смотреть'",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Неудалось добавить сериал в 'Буду смотреть'",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }

    private fun setAccountState(accountStates: AccountStates) {
        tvState = accountStates
        toggle_favorite.isChecked = accountStates.favorite
        toggle_watchlist.isChecked = accountStates.watchlist
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

    private fun setTVDetails(tv: TVDetails) {
        setHeader(tv.name, tv.voteAverage, tv.status, tv.genres, tv.firstAirDate)
        setTrailerButtonClickListener(tv.videos)
        setOverview(tv.overview)
        setFacts(tv)
        setPeoples(tv.credits.casts)
        setImages(tv.images, tv.backdropPath, tv.posterPath)
    }

    private fun setImageSlider(images: List<String>) {
        td_image_slider.adapter = ImageSliderAdapter(images)
    }

    private fun setPeoples(casts: List<Cast>) {
        t_persons.setAdapterWithFixedSize(CastAdapter(casts), true)
    }

    private fun setImages(images: Images, backdropPath: String?, posterPath: String?) {
        t_backdrop_image.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_780 + backdropPath)
        t_poster_image.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_500 + posterPath)

        setImageSlider(images.backdrops.map { imageInfo -> imageInfo.filePath }.take(6))
        setClickListenerForImages(images)
    }

    private fun setFacts(tv: TVDetails?) {
        if (tv == null) return

        setAirDate(tv.firstAirDate, tv.lastAirDate)
        setOriginalLanguage(tv.originalLanguage)
        setOriginalTitle(tv.originalName)
        setSeasonsAndEpisodes(tv.numberOfSeasons, tv.numberOfEpisodes)
        setRuntime(tv.episodeRunTime)
        setProductionCompanies(tv.productionCompanies)
    }

    private fun setProductionCompanies(productionCompanies: List<ProductionCompany>) {
        t_companies.text = viewBinding.root.context.combineString(
            getString(R.string.details_company_production),
            productionCompanies.joinToString(", ") { it.name }
        )
    }

    private fun setOverview(overview: String) {
        if (overview.isBlank()) {
            t_overview_empty.setVisibilityOption(true)
            t_overview.setVisibilityOption(false)
        } else {
            t_overview_empty.setVisibilityOption(false)
            t_overview.setVisibilityOption(true)
            t_overview.text = overview
        }
    }

    private fun setRuntime(episodeRunTime: List<Int>) {
        t_runtime.text =
            viewBinding.root.context.combineString(getString(R.string.details_runtime), episodeRunTime.toString())
    }

    private fun setSeasonsAndEpisodes(numberOfSeasons: Int, numberOfEpisodes: Int) {
        t_seasons_and_series.text =
            viewBinding.root.context.combineString(
                getString(R.string.details_fact_seasons_and_episodes),
                ("$numberOfSeasons сезон(ов) $numberOfEpisodes серий")
            )
    }

    private fun setOriginalTitle(originalName: String) {
        t_original_title.text =
            viewBinding.root.context.combineString(getString(R.string.details_original_title), originalName)
    }

    private fun setOriginalLanguage(originalLanguage: String) {
        t_original_language.text = viewBinding.root.context.combineString(
            getString(R.string.details_original_language),
            Locale(originalLanguage).displayLanguage.capitalize()
        )
    }

    private fun setHeader(
        title: String,
        voteAverage: Double,
        status: String,
        genres: List<Genre>,
        releaseDate: String
    ) {
        t_title.text = title
        t_vote_average.text = voteAverage.toString()
        t_status.text = viewBinding.root.context.combineString(getString(R.string.details_status), status)
        vote_average_indicator.setIndicatorColor(voteAverage)

        setGenres(genres, releaseDate)
    }

    private fun setAirDate(firstAirDate: String, lastAirDate: String) {
        val klockInstance = KlockLocale.default.formatDateLong

        val dateFormat = getString(R.string.details_yyyy_mm_dd)

        val formatFirstAirDate = klockInstance.format(firstAirDate.toDate(dateFormat))
        val formatLastAirDate = klockInstance.format(lastAirDate.toDate(dateFormat))

        t_first_air_date.text =
            viewBinding.root.context.combineString(getString(R.string.details_fact_first_air_date), formatFirstAirDate)
        t_last_air_date.text =
            viewBinding.root.context.combineString(getString(R.string.details_fact_last_air_date), formatLastAirDate)
    }

    private fun setGenres(genres: List<Genre>, releaseDate: String) {
        val genresFormat = genres.joinToString(", ") { it.name.capitalize() }

        t_add_info.text = getString(
            R.string.details_short_info_mask,
            releaseDate.toDate(getString(R.string.details_yyyy_mm_dd)).yearInt.toString(),
            genresFormat
        )
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
        return BottomSheetBehavior.from(td_bottom_sheet).state == BottomSheetBehavior.STATE_COLLAPSED
    }
}
