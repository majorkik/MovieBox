package com.majorik.moviebox.ui.language

import android.os.Bundle
import android.view.View
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.language.LanguageAdapter
import com.majorik.moviebox.extensions.setWindowTransparency
import com.majorik.moviebox.extensions.updateLanguage
import com.majorik.moviebox.extensions.updateMargin
import com.majorik.moviebox.ui.base.BaseSlidingActivity
import kotlinx.android.synthetic.main.activity_languages.*

class LanguageActivity : BaseSlidingActivity() {

    override fun getRootView(): View = window.decorView.rootView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_languages)

        setWindowTransparency(::updateMargins)

        list_languages.adapter = LanguageAdapter {
            updateLanguage(it)
        }

        list_languages.setHasFixedSize(true)
    }

    private fun updateMargins(statusBarSize: Int, @Suppress("UNUSED_PARAMETER") navigationBarSize: Int) {
        settings_toolbar.updateMargin(top = statusBarSize)
    }

    override fun onSlidingStarted() {}

    override fun onSlidingFinished() {}

    override fun canSlideDown(): Boolean = true
}
