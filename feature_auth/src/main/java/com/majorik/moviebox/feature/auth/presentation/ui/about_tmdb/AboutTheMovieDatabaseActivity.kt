package com.majorik.moviebox.feature.auth.presentation.ui.about_tmdb

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.doOnApplyWindowInsets
import com.majorik.library.base.extensions.updateMargin
import com.majorik.moviebox.feature.auth.R
import com.majorik.moviebox.feature.auth.databinding.ActivityAboutTheMovieDatabaseBinding

class AboutTheMovieDatabaseActivity : AppCompatActivity(R.layout.activity_about_the_movie_database) {

    private val viewBinding: ActivityAboutTheMovieDatabaseBinding by viewBinding(R.id.about_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateMargins()

        configWebView()
        setClickListeners()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configWebView() {
        viewBinding.webview.run {
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.userAgentString = "Android"
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()

            loadUrl(UrlConstants.ABOUT_TMDB)
        }
    }

    private fun setClickListeners() {
        viewBinding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun updateMargins() {
        viewBinding.root.doOnApplyWindowInsets { _, windowInsetsCompat, _ ->
            viewBinding.aboutToolbar.updateMargin(top = windowInsetsCompat.systemWindowInsetTop)

            windowInsetsCompat
        }
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (Build.VERSION.SDK_INT in 21..25 && (resources.configuration.uiMode == resources.configuration.uiMode)) {
            return
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }
}
