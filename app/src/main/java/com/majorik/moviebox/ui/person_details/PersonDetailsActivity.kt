package com.majorik.moviebox.ui.person_details

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import com.majorik.domain.constants.AppConfig
import com.majorik.domain.constants.UrlConstants
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.PersonFilmographyPagerAdapter
import com.majorik.moviebox.extensions.*
import com.majorik.moviebox.ui.base.BaseSlidingActivity
import com.majorik.moviebox.ui.person_details.biography.BiographyDialog
import kotlinx.android.synthetic.main.activity_person_details.*
import kotlinx.android.synthetic.main.layout_person_credits.*
import org.koin.android.viewmodel.ext.android.viewModel

class PersonDetailsActivity : BaseSlidingActivity() {

    private val personViewModel: PersonDetailsViewModel by viewModel()

    private var personId: Int = 0

    override fun getRootView(): View = window.decorView.rootView

    private var filmographyAdapter: PersonFilmographyPagerAdapter? = null

    private var isGrid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)

        setWindowTransparency(::updateMargins)

        setSupportActionBar(md_toolbar)

//        window.navigationBarColor = ContextCompat.getColor(this, R.color.colorAccent)

        supportActionBar?.run {
            setDisplayUseLogoEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        fetchPersonDetails(intent.extras)
        setObservers()
        setClickListeners()
        setBottomSheetListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun updateMargins(statusBarSize: Int, @Suppress("UNUSED_PARAMETER") navigationBarSize: Int) {
        md_toolbar.updateMargin(top = statusBarSize)
//        person_layout.updateMargin(bottom = navigationBarSize)
        header_bottom_sheet.minimumHeight = statusBarSize
    }

    override fun onSlidingStarted() {}

    override fun onSlidingFinished() {}

    override fun canSlideDown(): Boolean =
        BottomSheetBehavior.from(p_bottom_sheet).state == BottomSheetBehavior.STATE_COLLAPSED

    private fun setClickListeners() {
        btn_close.setOnClickListener {
            if (BottomSheetBehavior.from(p_bottom_sheet).state == BottomSheetBehavior.STATE_COLLAPSED) {
                BottomSheetBehavior.from(p_bottom_sheet).state =
                    BottomSheetBehavior.STATE_EXPANDED
            } else {
                BottomSheetBehavior.from(p_bottom_sheet).state =
                    BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        btn_view_type.setOnClickListener {
            isGrid = !isGrid

            filmographyAdapter?.changeViewType(isGrid)

            if (isGrid) {
                btn_view_type.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_list))
            } else {
                btn_view_type.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_module
                    )
                )
            }
        }
    }

    private fun setBottomSheetListener() {
        val bottomSheet = BottomSheetBehavior.from(p_bottom_sheet)
        bottomSheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                btn_close.rotation = (180 * (1 - slideOffset))
                line_bottom_sheet_view.alpha = (1 - slideOffset)
                btn_view_type.alpha = slideOffset
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                btn_view_type.isEnabled = newState == BottomSheetBehavior.STATE_EXPANDED
                setStatusBarModeForApi24(newState != BottomSheetBehavior.STATE_EXPANDED)
            }
        })
    }

    private fun setupPager() {
        val pagerTitles: Array<String> =
            arrayOf(getString(R.string.movies), getString(R.string.tvs))
        val pagerAdapter = filmographyAdapter

        p_view_pager.adapter = pagerAdapter

        TabLayoutMediator(p_tab_layout, p_view_pager) { tab, position ->
            tab.text = pagerTitles.getOrNull(position)
        }.attach()
    }

    private fun fetchPersonDetails(extras: Bundle?) {
        if (extras != null) {
            personId = extras.getInt("id")
            personViewModel.fetchPersonDetails(
                personId,
                AppConfig.REGION,
                "movie_credits,tv_credits,images,tagged_images"
            )
        }
    }

    private fun setObservers() {
        personViewModel.personDetailsLiveData.observe(this, Observer { personDetails ->
            person_name.text = personDetails.name
            person_translate_name.text = personDetails.alsoKnownAs.getOrNull(0) ?: ""
            person_type.text = personDetails.knownForDepartment

            btn_biography.setOnClickListener {
                BiographyDialog.newInstance(
                    personDetails.biography,
                    personDetails.birthday,
                    personDetails.placeOfBirth
                ).show(supportFragmentManager, "biography_dialog")
            }

            displayPersonMainPhoto(personDetails.profilePath)

            p_bottom_sheet.setVisibilityOption(true)

            filmographyAdapter = PersonFilmographyPagerAdapter(
                personDetails.movieCredits.cast,
                personDetails.tvCredits.cast
            )

            setupPager()
        })
    }

    private fun displayPersonMainPhoto(profilePath: String?) {
        person_profile_image.displayImageWithCenterCrop(UrlConstants.TMDB_SIZE_ORIGINAL + profilePath)
//        person_profile_image.setGrayscaleTransformation()
        person_image_container.setVisibilityOption(!profilePath.isNullOrEmpty())
    }
}
