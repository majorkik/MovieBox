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

        updateMargins()

        fetchPersonDetails()
        setObservers()
        setClickListeners()
        setBottomSheetListener()
    }

    private fun updateMargins() {
        viewBinding.root.doOnApplyWindowInsets { view, insets, rect ->
            viewBinding.mdToolbar.updateMargin(top = insets.systemWindowInsetTop)
            viewBinding.layoutCredits.headerBottomSheet.minimumHeight = insets.systemWindowInsetTop

            insets
        }
    }

    private fun setupPager() {
        val pagerTitles: Array<String> =
            arrayOf(
                getString(R.string.details_movies),
                getString(R.string.details_tvs)
            )
        val pagerAdapter = pagerAdapter

        viewBinding.layoutCredits.pViewPager.adapter = pagerAdapter

        TabLayoutMediator(viewBinding.layoutCredits.pTabLayout, viewBinding.layoutCredits.pViewPager) { tab, position ->
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
        viewBinding.layoutCredits.btnClose.setOnClickListener {
            actionFilmographyCloseState()
        }

        viewBinding.layoutCredits.btnViewType.setOnClickListener {
            actionChangeViewTypePagerItems()
        }
    }

    private fun setBottomSheetListener() {
        val bottomSheet = BottomSheetBehavior.from(viewBinding.layoutCredits.root)
        bottomSheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                viewBinding.layoutCredits.btnClose.rotation = (180 * (1 - slideOffset))
                viewBinding.layoutCredits.lineBottomSheetView.alpha = (1 - slideOffset)

                viewBinding.layoutCredits.personToolbar.alpha = slideOffset
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                viewBinding.layoutCredits.btnViewType.isEnabled = newState == BottomSheetBehavior.STATE_EXPANDED
                activity?.setStatusBarMode(newState != BottomSheetBehavior.STATE_EXPANDED)
            }
        })
    }

    /**
     * Actions
     */

    private fun actionFilmographyCloseState() {
        val bottomSheet = BottomSheetBehavior.from(viewBinding.layoutCredits.root)

        when (bottomSheet.state) {
            BottomSheetBehavior.STATE_COLLAPSED -> {
                bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
            }
            else -> {
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun actionChangeViewTypePagerItems() {
        isGrid = !isGrid

        pagerAdapter?.changeViewType(isGrid)

        val viewTypeIcon = if (isGrid) {
            ContextCompat.getDrawable(
                viewBinding.root.context,
                AppResources.drawable.ic_list_agenda_24
            )
        } else {
            ContextCompat.getDrawable(viewBinding.root.context, AppResources.drawable.ic_module_3x2_24)
        }

        viewBinding.layoutCredits.btnViewType.setImageDrawable(viewTypeIcon)
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
        viewBinding.personName.text = personDetails.name
        viewBinding.personTranslateName.text = personDetails.alsoKnownAs.getOrNull(0) ?: ""
        viewBinding.personType.text = personDetails.knownForDepartment

        viewBinding.btnBiography.setOnClickListener {
            BiographyDialog.newInstance(
                personDetails.biography,
                personDetails.birthday,
                personDetails.placeOfBirth
            ).show(childFragmentManager, "biography_dialog")
        }

        displayPersonMainPhoto(personDetails.profilePath)

        viewBinding.layoutCredits.root.setVisibilityOption(true)

        pagerAdapter = PersonFilmographyPagerAdapter(
            ::openMovieDetails,
            ::openTVDetails,
            personDetails.movieCredits.cast,
            personDetails.tvCredits.cast
        )

        setupPager()
    }

    private fun displayPersonMainPhoto(profilePath: String?) {
        viewBinding.personProfileImage.displayImageWithCenterCrop(UrlConstants.TMDB_SIZE_ORIGINAL + profilePath)
//        person_profile_image.setGrayscaleTransformation()
        viewBinding.personImageContainer.setVisibilityOption(!profilePath.isNullOrEmpty())
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
        return BottomSheetBehavior.from(viewBinding.layoutCredits.root).state == BottomSheetBehavior.STATE_COLLAPSED
    }
}
