package com.majorik.moviebox.feature.search.presentation.ui.filters

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.extensions.loadIntentOrReturnNull
import com.majorik.library.base.extensions.setVisibilityOption
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.genre.Genre
import com.majorik.moviebox.feature.search.R
import com.majorik.moviebox.feature.search.databinding.DialogDiscoverFiltersBinding
import com.orhanobut.logger.Logger
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.math.roundToInt
import com.majorik.moviebox.R as AppRes

class DiscoverFiltersBottomSheetFragment : BottomSheetDialogFragment() {
    private val viewBinding: DialogDiscoverFiltersBinding by viewBinding()

    private val viewModel: DiscoverFiltersViewModel by viewModel()

    private val selectedMovieGenres = mutableSetOf<Int>()
    private val selectedTVGenres = mutableSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, AppRes.style.Widget_AppTheme_BottomSheet)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_discover_filters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchGenres()
        setClickListeners()
        initReleaseDateRangeBar()
        initReleaseDateYearBar()
        initRatingBar()
        setAnimateReleaseDateBlock()
        setObservers()
    }

    private fun setObservers() {
        viewModel.movieGenresLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (viewBinding.sFilterType.isChecked) { // Если выбран тип - Фильмы
                addGenresBlock(it.genres)
            }
        })

        viewModel.tvGenresLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (!viewBinding.sFilterType.isChecked) { // Если выбран тип - Сериалы
                addGenresBlock(it.genres)
            }
        })
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
                changeFilterTypeElements()
                chipGenreSelectedAction()
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

    private fun changeFilterTypeElements() {
        if (viewBinding.sFilterType.isChecked) {
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
    }

    /**
     * Release Date block
     */

    private fun initReleaseDateRangeBar() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        viewBinding.releaseDateRangeBar.apply {
            setRange(RELEASE_DATE_MIN_YEAR.toFloat(), (currentYear + RELEASE_DATE_RANGE_OFFSET).toFloat(), 1f)

            setProgress(
                viewBinding.releaseDateRangeBar.minProgress,
                viewBinding.releaseDateRangeBar.maxProgress
            )
        }

        setReleaseDateRangeBarListener(currentYear)
    }

    private fun setReleaseDateRangeBarListener(currentYear: Int) {
        val maxProgress = viewBinding.releaseDateRangeBar.maxProgress.roundToInt()

        viewBinding.releaseDateRangeBar.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }

            override fun onRangeChanged(view: RangeSeekBar?, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
                val leftInt = leftValue.roundToInt()
                val rightInt = rightValue.roundToInt()

                val maxYear =
                    if (maxProgress == rightInt) "${currentYear + RELEASE_DATE_RANGE_OFFSET}+" else rightInt
                        .toString()

                viewBinding.descriptionReleaseDate.text = "${leftInt} - ${maxYear}"
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }
        })
    }

    private fun initReleaseDateYearBar() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        viewBinding.releaseDateYearBar.apply {
            setRange(RELEASE_DATE_MIN_YEAR.toFloat(), (currentYear + RELEASE_DATE_SINGLE_OFFSET).toFloat(), 1f)

            setProgress(currentYear.toFloat())
        }

        setReleaseDateYearBarListener()
    }

    private fun setReleaseDateYearBarListener() {
        viewBinding.releaseDateYearBar.setOnRangeChangedListener(object : OnRangeChangedListener {
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
                displayReleaseDateRangeBar()
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
                releaseDateRangeBar.setVisibilityOption(false)
                releaseDateYearBar.setVisibilityOption(false)

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

    private fun displayReleaseDateRangeBar() {
        viewBinding.apply {
            releaseDateChipGroup.setVisibilityOption(false)
            releaseDateRangeBar.setVisibilityOption(true)

            val leftProgress = releaseDateRangeBar.leftSeekBar.progress
            val rightProgress = releaseDateRangeBar.rightSeekBar.progress
            releaseDateRangeBar.setProgress(leftProgress, rightProgress)
        }
    }

    private fun displayReleaseDateSingleBar() {
        viewBinding.apply {
            releaseDateChipGroup.setVisibilityOption(false)
            releaseDateYearBar.setVisibilityOption(true)

            val progress = releaseDateYearBar.leftSeekBar.progress
            releaseDateYearBar.setProgress(progress)
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
            setProgress(0f, 100f)

        }

        setRatingBarListener()
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
                if (viewBinding.sFilterType.isChecked) {
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
            selectedMovieGenres.add(genreId)
        } else {
            selectedMovieGenres.remove(genreId)
        }

        chipGenreSelectedAction()
    }

    private fun clickTVGenre(chipChecked: Boolean, genreId: Int) {
        if (chipChecked) {
            selectedTVGenres.add(genreId)
        } else {
            selectedTVGenres.remove(genreId)
        }

        chipGenreSelectedAction()
    }

    private fun checkGenresSelected(genreId: Int): Boolean {
        return if (viewBinding.sFilterType.isChecked) {
            selectedMovieGenres.contains(genreId)
        } else {
            selectedTVGenres.contains(genreId)
        }
    }

    private fun chipGenreSelectedAction() {
        val countActiveGenres = if (viewBinding.sFilterType.isChecked) {
            selectedMovieGenres.count()
        } else {
            selectedTVGenres.count()
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
        val keywordResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Logger.i(it.data?.getStringExtra("da") ?: "is null")
        }

        keywordResult.launch(ScreenLinks.movieDetails.loadIntentOrReturnNull())
    }

    /**
     * Credits block
     */

    private fun openSearchCreditsScreen() {

    }

    /**
     * Companion object
     */

    companion object {
        const val RELEASE_DATE_MIN_YEAR = 1940
        const val RELEASE_DATE_RANGE_OFFSET = 5
        const val RELEASE_DATE_SINGLE_OFFSET = 10

        private var releaseDateType = ReleaseDateType.ANY
    }
}
