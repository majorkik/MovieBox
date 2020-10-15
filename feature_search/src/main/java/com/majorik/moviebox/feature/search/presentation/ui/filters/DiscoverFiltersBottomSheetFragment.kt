package com.majorik.moviebox.feature.search.presentation.ui.filters

import android.animation.LayoutTransition
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import com.majorik.library.base.extensions.setVisibilityOption
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.genre.Genre
import com.majorik.moviebox.feature.search.R
import com.majorik.moviebox.feature.search.databinding.DialogDiscoverFiltersBinding
import com.majorik.moviebox.feature.search.domain.enums.*
import com.majorik.moviebox.feature.search.domain.models.discover.DiscoverFiltersModel
import com.orhanobut.logger.Logger
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.math.roundToInt
import com.majorik.moviebox.R as AppRes

class DiscoverFiltersBottomSheetFragment : BottomSheetDialogFragment() {
    private val viewBinding: DialogDiscoverFiltersBinding by viewBinding()

    private val viewModel: DiscoverFiltersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, AppRes.style.Widget_AppTheme_BottomSheet)

        viewModel.filtersModel =
            (arguments?.getSerializable(KEY_FILTERS) as? DiscoverFiltersModel) ?: DiscoverFiltersModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_discover_filters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchGenres()
        setClickListeners()
        setListeners()

        initReleaseDateRange()
        initReleaseDateSingle()
        initRatingBar()

        setAnimateReleaseDateBlock()
        setObservers()
        setupFilters()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        saveFiltersToModel()

        setFragmentResult(
            KEY_DISCOVER_FILTERS_REQUEST,
            Bundle().apply {
                putSerializable(KEY_FILTERS_MODEL, viewModel.filtersModel)
            }
        )
    }

    private fun setObservers() {
        viewModel.movieGenresLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            getDiscoverType().isMoviesTypeWithAction { // Если выбран тип - Фильмы
                addGenresBlock(it.genres)
            }
        })

        viewModel.tvGenresLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                getDiscoverType().isTVsTypeWithAction { // Если выбран тип - Сериалы
                    addGenresBlock(it.genres)
                }
            }
        )
    }

    private fun fetchGenres() {
        lifecycleScope.launchWhenResumed {
            viewModel.getMovieGenres()
            viewModel.getTVGenres()
        }
    }

    private fun setClickListeners() {
        viewBinding.apply {
            layoutReleaseDate.setOnClickListener {
                displayReleaseDateChipsMenu()
            }

            chipDateAnyDates.setOnClickListener {
                releaseDateType = ReleaseDateType.ANY

                changeReleaseDateType()
            }

            chipDateBetweenDates.setOnClickListener {
                releaseDateType = ReleaseDateType.BETWEEN_DATES

                changeReleaseDateType()
            }

            chipDateOneYear.setOnClickListener {
                releaseDateType = ReleaseDateType.ONE_YEAR

                changeReleaseDateType()
            }

            sFilterType.setOnClickListener {
                chipGenresSelectedChanged()
                changeFiltersType()
            }

            genresBlock.setOnClickListener {
                displayGenresBlock()
            }

            btnAddKeywords.setOnClickListener {
                openSearchKeywordsScreen()
            }

            btnAddCredits.setOnClickListener {
                openSearchCreditsScreen()
            }
        }
    }

    private fun setListeners() {
        setReleaseDateRangeListener()
        setReleaseDateSingleListener()
        setRatingBarListener()
    }

    private fun changeFiltersType() {
        if (getDiscoverType().isMoviesType()) {
            if (viewModel.movieGenresLiveData.value?.genres.isNullOrEmpty()) {
                viewModel.getMovieGenres()
            } else {
                addGenresBlock(viewModel.movieGenresLiveData.value!!.genres)
            }
        } else {
            if (viewModel.tvGenresLiveData.value?.genres.isNullOrEmpty()) {
                viewModel.getTVGenres()
            } else {
                addGenresBlock(viewModel.tvGenresLiveData.value!!.genres)
            }
        }

        setupIncludeAdultUI()
        setupIncludeTVsWithoutDates()
    }

    private fun saveFiltersToModel() {
        viewModel.filtersModel?.apply {
            discoverType = getDiscoverType()

            sortType = getSortType()
            sortFeature = getSortFeature()

            minYear = getMinReleaseDate()
            maxYear = getMaxReleaseDate()

            voteAverageMin = viewBinding.ratingBar.leftSeekBar.progress
            voteAverageMax = viewBinding.ratingBar.rightSeekBar.progress

            includeAdult = viewBinding.sIncludeAdult.isChecked

            movieGenresIds = viewModel.selectedMovieGenres.toList()
            tvGenresIds = viewModel.selectedTVGenres.toList()

            includeTVsWithNullAirDate = viewBinding.sIncludeTvsWithNullDate.isChecked
        }
    }

    /**
     * Release Date block
     */

    private fun initReleaseDateRange() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        viewBinding.releaseDateRange.apply {
            setRange(RELEASE_DATE_MIN_YEAR.toFloat(), (currentYear + RELEASE_DATE_RANGE_OFFSET).toFloat(), 1f)

            setProgress(minProgress, maxProgress)
        }
    }

    private fun setReleaseDateRangeListener() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val maxProgress = viewBinding.releaseDateRange.maxProgress.roundToInt()

        viewBinding.releaseDateRange.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }

            override fun onRangeChanged(view: RangeSeekBar?, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
                val leftInt = leftValue.roundToInt()
                val rightInt = rightValue.roundToInt()

                val maxYear =
                    if (maxProgress == rightInt) "${currentYear + RELEASE_DATE_RANGE_OFFSET}+" else rightInt
                        .toString()

                viewBinding.descriptionReleaseDate.text = "$leftInt - $maxYear"
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }
        })
    }

    private fun initReleaseDateSingle() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        viewBinding.releaseDateSingle.apply {
            setRange(RELEASE_DATE_MIN_YEAR.toFloat(), (currentYear + RELEASE_DATE_SINGLE_OFFSET).toFloat(), 1f)

            setProgress(currentYear.toFloat())
        }
    }

    private fun setReleaseDateSingleListener() {
        viewBinding.releaseDateSingle.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }

            override fun onRangeChanged(view: RangeSeekBar?, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
                viewBinding.descriptionReleaseDate.text = "${leftValue.roundToInt()}"
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }
        })
    }

    private fun changeReleaseDateType() {
        when (releaseDateType) {
            ReleaseDateType.ANY -> {
                displayAnyReleaseDate()
            }

            ReleaseDateType.BETWEEN_DATES -> {
                displayReleaseDateRange()
            }

            ReleaseDateType.ONE_YEAR -> {
                displayReleaseDateSingleBar()
            }
        }
    }

    private fun displayReleaseDateChipsMenu() {
        viewBinding.apply {
            if (releaseDateChipGroup.visibility == View.VISIBLE) {
                changeReleaseDateType()
            } else {
                releaseDateRange.setVisibilityOption(false)
                releaseDateSingle.setVisibilityOption(false)
                releaseDateChipGroup.setVisibilityOption(true)
            }
        }
    }

    private fun displayAnyReleaseDate() {
        viewBinding.apply {
            releaseDateChipGroup.setVisibilityOption(false)

            descriptionReleaseDate.text = getString(R.string.filters_dialog_release_date_description_any)
        }
    }

    private fun displayReleaseDateRange() {
        viewBinding.apply {
            releaseDateChipGroup.setVisibilityOption(false)
            releaseDateRange.setVisibilityOption(true)

            val leftProgress = releaseDateRange.leftSeekBar.progress
            val rightProgress = releaseDateRange.rightSeekBar.progress
            releaseDateRange.setProgress(leftProgress, rightProgress)
        }
    }

    private fun displayReleaseDateSingleBar() {
        viewBinding.apply {
            releaseDateChipGroup.setVisibilityOption(false)
            releaseDateSingle.setVisibilityOption(true)

            val progress = releaseDateSingle.leftSeekBar.progress
            releaseDateSingle.setProgress(progress)
        }
    }

    private fun setAnimateReleaseDateBlock() {
        val transition = LayoutTransition()
        transition.setAnimateParentHierarchy(false)
        viewBinding.releaseDateChipGroup.layoutTransition = null
        viewBinding.layoutReleaseDate.layoutTransition = transition
    }

    /**
     * Raring block
     */

    private fun initRatingBar() {
        viewBinding.ratingBar.apply {
            isStepsAutoBonding = true
            steps = 1
            setRange(0f, 100f, 10f)
        }
    }

    private fun setRatingBarListener() {
        viewBinding.ratingBar.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }

            override fun onRangeChanged(view: RangeSeekBar, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
                val left = leftValue.roundToInt()
                val right = rightValue.roundToInt()
                if (left == 0 && right == 100) {
                    viewBinding.descriptionRating.text = getString(R.string.filters_dialog_rating_description_any)
                } else {
                    viewBinding.descriptionRating.text = "${left / 10f} - ${right / 10f}"
                }
            }

            override fun onStopTrackingTouch(view: RangeSeekBar, isLeft: Boolean) {
            }
        })
    }

    /**
     * Genres block
     */

    private fun addGenresBlock(items: List<Genre>) {
        viewBinding.genresChipGroup.removeAllViews()
        items.forEach { genre ->
            val chip = LayoutInflater.from(context)
                .inflate(R.layout.item_chip_genre, viewBinding.genresChipGroup, false) as Chip
            chip.text = genre.name
            chip.isChecked = checkGenresSelected(genre.id)

            chip.setOnClickListener {
                if (getDiscoverType().isMoviesType()) {
                    clickMovieGenre(chip.isChecked, genre.id)
                } else {
                    clickTVGenre(chip.isChecked, genre.id)
                }
            }

            viewBinding.genresChipGroup.addView(chip)
        }
    }

    private fun clickMovieGenre(chipChecked: Boolean, genreId: Int) {
        if (chipChecked) {
            viewModel.selectedMovieGenres.add(genreId)
        } else {
            viewModel.selectedMovieGenres.remove(genreId)
        }

        chipGenresSelectedChanged()
    }

    private fun clickTVGenre(chipChecked: Boolean, genreId: Int) {
        if (chipChecked) {
            viewModel.selectedTVGenres.add(genreId)
        } else {
            viewModel.selectedTVGenres.remove(genreId)
        }

        chipGenresSelectedChanged()
    }

    private fun checkGenresSelected(genreId: Int): Boolean {
        return if (getDiscoverType().isMoviesType()) {
            viewModel.selectedMovieGenres.contains(genreId)
        } else {
            viewModel.selectedTVGenres.contains(genreId)
        }
    }

    private fun chipGenresSelectedChanged() {
        val countActiveGenres = if (getDiscoverType().isMoviesType()) {
            viewModel.selectedMovieGenres.count()
        } else {
            viewModel.selectedTVGenres.count()
        }

        if (countActiveGenres == 0) {
            viewBinding.descriptionGenres.text = getString(R.string.filters_dialog_genres_description_any)
        } else {
            viewBinding.descriptionGenres.text =
                getString(R.string.filters_dialog_selected_genres_format).format(countActiveGenres)
        }
    }

    private fun displayGenresBlock() {
        if (viewBinding.genresChipGroup.visibility == View.VISIBLE) {
            viewBinding.genresChipGroup.setVisibilityOption(false)
        } else {
            viewBinding.genresChipGroup.setVisibilityOption(true)
        }
    }

    /**
     * Keywords block
     */

    private fun openSearchKeywordsScreen() {
    }

    /**
     * Credits block
     */

    private fun openSearchCreditsScreen() {
    }

    /**
     * Методы для изменения интерфейса по фильтрам
     */

    private fun setupFilters() {
        if (viewModel.filtersModel == null) viewModel.filtersModel = DiscoverFiltersModel()

        viewBinding.sFilterType.isChecked = viewModel.filtersModel?.discoverType?.isTVsType() ?: false

        viewModel.selectedMovieGenres.addAll(viewModel.filtersModel?.movieGenresIds ?: emptyList())
        viewModel.selectedTVGenres.addAll(viewModel.filtersModel?.tvGenresIds ?: emptyList())

        setupYearsUI()
        setupVoteAverageUI()
        setupSortUI()
        setupIncludeAdultUI()
        setupIncludeTVsWithoutDates()

        chipGenresSelectedChanged()
    }

    private fun setupYearsUI() {
        viewBinding.apply {
            if (viewModel.filtersModel?.minYear == null && viewModel.filtersModel?.maxYear == null) {
                releaseDateType = ReleaseDateType.ANY
                chipDateAnyDates.isChecked = true
            } else if (viewModel.filtersModel?.minYear != null && viewModel.filtersModel?.maxYear == null) {
                releaseDateType = ReleaseDateType.ONE_YEAR
                chipDateOneYear.isChecked = true
                releaseDateSingle.setProgress(
                    viewModel.filtersModel?.minYear?.toFloat() ?: Calendar.getInstance().get(Calendar.YEAR).toFloat()
                )
            } else {
                releaseDateType = ReleaseDateType.BETWEEN_DATES

                chipDateBetweenDates.isChecked = true

                Logger.i("${viewModel.filtersModel?.minYear}, ${viewModel.filtersModel?.maxYear}")

                releaseDateRange.setProgress(
                    viewModel.filtersModel?.minYear?.toFloat() ?: releaseDateRange.minProgress,
                    viewModel.filtersModel?.maxYear?.toFloat() ?: releaseDateRange.maxProgress
                )
            }
        }

        changeReleaseDateType()
    }

    private fun setupVoteAverageUI() {
        viewBinding.apply {
            ratingBar.setProgress(
                viewModel.filtersModel?.voteAverageMin ?: ratingBar.minProgress,
                viewModel.filtersModel?.voteAverageMax ?: ratingBar.maxProgress
            )
        }
    }

    private fun setupSortUI() {
        viewBinding.apply {
            sSortBy.isChecked = viewModel.filtersModel?.sortFeature == SortFeature.ASCENDING

            when (viewModel.filtersModel?.sortType) {
                SortType.BY_POPULARITY -> chipSortPopularity.isChecked = true

                SortType.BY_DATE -> chipSortReleaseDate.isChecked = true

                SortType.BY_VOTE_AVERAGE -> chipSortRating.isChecked = true

                SortType.BY_VOTE_COUNT -> chipSortVoteCount.isChecked = true

                null -> chipSortPopularity.isChecked = true
            }
        }
    }

    private fun setupIncludeTVsWithoutDates() {
        viewBinding.sIncludeTvsWithNullDate.isChecked = viewModel.filtersModel?.includeTVsWithNullAirDate ?: false

        if (getDiscoverType().isMoviesType()) {
            viewBinding.sIncludeTvsWithNullDate.isEnabled = false
            viewBinding.sIncludeTvsWithNullDate.alpha = 0.33f
        } else {
            viewBinding.sIncludeTvsWithNullDate.isEnabled = true
            viewBinding.sIncludeTvsWithNullDate.alpha = 1f
        }
    }

    private fun setupIncludeAdultUI() {
        viewBinding.sIncludeAdult.isChecked = viewModel.filtersModel?.includeAdult ?: false

        if (getDiscoverType().isMoviesType()) {
            viewBinding.sIncludeAdult.isEnabled = true
            viewBinding.sIncludeAdult.alpha = 1f
        } else {
            viewBinding.sIncludeAdult.isEnabled = false
            viewBinding.sIncludeAdult.alpha = 0.33f
        }
    }

    /**
     * Методы для получения фильтров из UI
     */

    private fun getDiscoverType() = if (viewBinding.sFilterType.isChecked) {
        DiscoverType.TVS
    } else {
        DiscoverType.MOVIES
    }

    private fun getSortFeature() = if (viewBinding.sSortBy.isChecked) {
        SortFeature.ASCENDING
    } else {
        SortFeature.DESCENDING
    }

    private fun getMinReleaseDate() = when (releaseDateType) {
        ReleaseDateType.ANY -> {
            null
        }

        ReleaseDateType.ONE_YEAR -> {
            viewBinding.releaseDateSingle.leftSeekBar.progress.roundToInt()
        }

        ReleaseDateType.BETWEEN_DATES -> {
            viewBinding.releaseDateRange.leftSeekBar.progress.roundToInt()
        }
    }

    private fun getMaxReleaseDate() = when (releaseDateType) {
        ReleaseDateType.ANY -> {
            null
        }

        ReleaseDateType.ONE_YEAR -> {
            null
        }

        ReleaseDateType.BETWEEN_DATES -> {
            viewBinding.releaseDateRange.rightSeekBar.progress.roundToInt()
        }
    }

    private fun getSortType(): SortType {
        if (viewBinding.chipSortPopularity.isChecked) return SortType.BY_POPULARITY

        if (viewBinding.chipSortRating.isChecked) return SortType.BY_VOTE_AVERAGE

        if (viewBinding.chipSortReleaseDate.isChecked) return SortType.BY_DATE

        if (viewBinding.chipSortVoteCount.isChecked) return SortType.BY_VOTE_COUNT

        return SortType.BY_POPULARITY
    }

    /**
     * Companion object
     */

    companion object {
        const val KEY_FILTERS = "key_filters"
        const val KEY_DISCOVER_FILTERS_REQUEST = "key_discover_filters_request"
        const val KEY_FILTERS_MODEL = "key_filters_model"

        const val RELEASE_DATE_MIN_YEAR = 1940
        const val RELEASE_DATE_RANGE_OFFSET = 5
        const val RELEASE_DATE_SINGLE_OFFSET = 10

        private var releaseDateType = ReleaseDateType.ANY

        fun newInstance(filtersModel: DiscoverFiltersModel) = DiscoverFiltersBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putSerializable(KEY_FILTERS, filtersModel)
            }
        }
    }
}
