package com.majorik.moviebox.ui.person_details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.majorik.domain.constants.UrlConstants
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.*
import com.majorik.moviebox.ui.base.BaseSlidingActivity
import kotlinx.android.synthetic.main.activity_person_details.*
import kotlinx.android.synthetic.main.layout_person_credits.*
import org.koin.android.viewmodel.ext.android.viewModel

class PersonDetailsActivity : BaseSlidingActivity() {

    private val personViewModel: PersonDetailsViewModel by viewModel()

    override fun getRootView(): View = window.decorView.rootView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)

        setWindowTransparency(::updateMargins)

        setSupportActionBar(md_toolbar)

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
    }

    override fun onSlidingStarted() {}

    override fun onSlidingFinished() {}

    override fun canSlideDown(): Boolean =
        BottomSheetBehavior.from(p_bottom_sheet).state == BottomSheetBehavior.STATE_COLLAPSED

    private fun setClickListeners() {
        btn_close.setOnClickListener {
            if (BottomSheetBehavior.from(p_bottom_sheet).state == BottomSheetBehavior.STATE_COLLAPSED) {
                BottomSheetBehavior.from(p_bottom_sheet).state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                BottomSheetBehavior.from(p_bottom_sheet).state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun setBottomSheetListener() {
        val bottomSheet = BottomSheetBehavior.from(p_bottom_sheet)
        bottomSheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                btn_close.rotation = 180 * slideOffset
                btn_filter.alpha = slideOffset
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                btn_filter.isEnabled = newState == BottomSheetBehavior.STATE_EXPANDED
            }
        })
    }

    private fun fetchPersonDetails(extras: Bundle?) {
        if (extras != null) {
            personViewModel.fetchPersonDetails(
                extras.getInt("id"),
                "ru-RU",
                "movie_credits,tv_credits,images,tagged_images"
            )
        }
    }

    private fun setObservers() {
        personViewModel.personDetailsLiveData.observe(this, Observer { personDetails ->
            person_name.text = personDetails.name
            person_translate_name.text = personDetails.alsoKnownAs.getOrNull(0) ?: ""
            person_type.text = personDetails.knownForDepartment

            displayPersonMainPhoto(personDetails.profilePath)
        })
    }

    private fun displayPersonMainPhoto(profilePath: String?) {
        person_profile_image.displayImageWithCenterCrop(UrlConstants.TMDB_SIZE_ORIGINAL + profilePath)
        person_profile_image.setGrayscaleTransformation()
        person_image_container.setVisibilityOption(!profilePath.isNullOrEmpty())
    }
}
