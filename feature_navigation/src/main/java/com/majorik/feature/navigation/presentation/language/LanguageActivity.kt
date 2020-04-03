package com.majorik.feature.navigation.presentation.language

import android.os.Bundle
import android.view.View
import com.majorik.feature.navigation.presentation.adapters.LanguageAdapter
import com.majorik.library.base.extensions.setWindowTransparency
import com.majorik.library.base.extensions.updateLanguage
import com.majorik.library.base.extensions.updateMargin
import com.majorik.library.base.base.BaseSlidingActivity
import kotlinx.android.synthetic.main.activity_languages.*

class LanguageActivity : BaseSlidingActivity() {

    override fun getRootView(): View = window.decorView.rootView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.majorik.feature.navigation.R.layout.activity_languages)

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
