package com.majorik.moviebox.feature.details.presentation.person_details

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import com.majorik.moviebox.feature.details.presentation.adapters.PersonFilmographyPagerAdapter
import com.majorik.library.base.extensions.*
import com.majorik.moviebox.feature.details.presentation.person_details.biography.BiographyDialog
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.library.base.view.PullDownLayout
import kotlinx.android.synthetic.main.dialog_fragment_person_details.*
import kotlinx.android.synthetic.main.layout_person_credits.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.majorik.moviebox.feature.details.R
import com.majorik.moviebox.feature.details.databinding.DialogFragmentPersonDetailsBinding
import com.majorik.moviebox.feature.details.domain.tmdbModels.person.PersonDetails
import kotlinx.coroutines.flow.collectLatest
import com.majorik.moviebox.R as AppResources

class PersonDetailsDialogFragment : DialogFragment(R.layout.dialog_fragment_person_details), PullDownLayout.Callback {

    private val viewBinding: DialogFragmentPersonDetailsBinding by viewBinding()

    private val viewModel: PersonDetailsViewModel by viewModel()

    private val args: PersonDetailsDialogFragmentArgs by navArgs()

    private var pagerAdapter: PersonFilmographyPagerAdapter? = null

    private var isGrid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, AppResources.style.AppTheme_DialogFragmentTransparentStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.root.setCallback(this)

        setWindowTransparency(::updateMargins)

        fetchPersonDetails()
        setObservers()
        setClickListeners()
        setBottomSheetListener()
    }

    private fun updateMargins(statusBarSize: Int, @Suppress("UNUSED_PARAMETER") navigationBarSize: Int) {
        md_toolbar.updateMargin(top = statusBarSize)
        header_bottom_sheet.minimumHeight = statusBarSize
    }

    private fun setupPager() {
        val pagerTitles: Array<String> =
            arrayOf(
                getString(R.string.details_movies),
                getString(R.string.details_tvs)
            )
        val pagerAdapter = pagerAdapter

        p_view_pager.adapter = pagerAdapter

        TabLayoutMediator(p_tab_layout, p_view_pager) { tab, position ->
            tab.text = pagerTitles.getOrNull(position)
        }.attach()
    }

    private fun setObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.personDetailsFlow.collectLatest { result ->
                result?.let { setPersonDetailsResult(it) }
            }
        }
    }

    private fun setClickListeners() {
        btn_close.setOnClickListener {
            actionFilmographyCloseState()
        }

        btn_view_type.setOnClickListener {
            actionChangeViewTypePagerItems()
        }
    }

    private fun setBottomSheetListener() {
        val bottomSheet = BottomSheetBehavior.from(p_bottom_sheet)
        bottomSheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                btn_close.rotation = (180 * (1 - slideOffset))
                line_bottom_sheet_view.alpha = (1 - slideOffset)

                person_toolbar.alpha = slideOffset
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                btn_view_type.isEnabled = newState == BottomSheetBehavior.STATE_EXPANDED
                activity?.setStatusBarMode(newState != BottomSheetBehavior.STATE_EXPANDED)
            }
        })
    }

    /**
     * Actions
     */

    private fun actionFilmographyCloseState() {
        if (BottomSheetBehavior.from(p_bottom_sheet).state == BottomSheetBehavior.STATE_COLLAPSED) {
            BottomSheetBehavior.from(p_bottom_sheet).state =
                BottomSheetBehavior.STATE_EXPANDED
        } else {
            BottomSheetBehavior.from(p_bottom_sheet).state =
                BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun actionChangeViewTypePagerItems() {
        isGrid = !isGrid

        pagerAdapter?.changeViewType(isGrid)

        val viewTypeIcon = if (isGrid) {
            ContextCompat.getDrawable(
                viewBinding.root.context,
                R.drawable.ic_list
            )
        } else {
            ContextCompat.getDrawable(viewBinding.root.context, AppResources.drawable.ic_module)
        }

        btn_view_type.setImageDrawable(viewTypeIcon)
    }

    /**
     * Requests
     */

    private fun fetchPersonDetails() {
        viewModel.fetchPersonDetails(
            args.id,
            AppConfig.REGION,
            "movie_credits,tv_credits,images,tagged_images"
        )
    }

    private fun openMovieDetails(id: Int) {
        findNavController().navigate(PersonDetailsDialogFragmentDirections.actionToMovieDetails(id))
    }

    private fun openTVDetails(id: Int) {
        findNavController().navigate(PersonDetailsDialogFragmentDirections.actionToTvDetails(id))
    }

    /**
     * Results
     */

    private fun setPersonDetailsResult(result: ResultWrapper<PersonDetails>) {
        when (result) {
            is ResultWrapper.Success -> {
                setPersonDetails(result.value)
            }
        }
    }

    /**
     * Details
     */

    private fun setPersonDetails(personDetails: PersonDetails) {
        person_name.text = personDetails.name
        person_translate_name.text = personDetails.alsoKnownAs.getOrNull(0) ?: ""
        person_type.text = personDetails.knownForDepartment

        btn_biography.setOnClickListener {
            BiographyDialog.newInstance(
                personDetails.biography,
                personDetails.birthday,
                personDetails.placeOfBirth
            ).show(childFragmentManager, "biography_dialog")
        }

        displayPersonMainPhoto(personDetails.profilePath)

        p_bottom_sheet.setVisibilityOption(true)

        pagerAdapter = PersonFilmographyPagerAdapter(
            ::openMovieDetails,
            ::openTVDetails,
            personDetails.movieCredits.cast,
            personDetails.tvCredits.cast
        )

        setupPager()
    }

    private fun displayPersonMainPhoto(profilePath: String?) {
        person_profile_image.displayImageWithCenterCrop(UrlConstants.TMDB_SIZE_ORIGINAL + profilePath)
//        person_profile_image.setGrayscaleTransformation()
        person_image_container.setVisibilityOption(!profilePath.isNullOrEmpty())
    }

    /**
     * PullDownLayout callbacks
     */

    override fun onPullStart() = Unit

    override fun onPull(progress: Float) = Unit

    override fun onPullCancel() = Unit

    override fun onPullComplete() {
        findNavController().popBackStack()
    }

    override fun pullDownReady(): Boolean {
        return BottomSheetBehavior.from(p_bottom_sheet).state == BottomSheetBehavior.STATE_COLLAPSED
    }
}
