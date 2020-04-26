package com.majorik.moviebox.feature.auth.presentation.ui.first_start

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.majorik.moviebox.feature.auth.domain.constants.AuthUrlConstants.BASE_AUTHENTICATE_URL
import com.majorik.moviebox.feature.auth.domain.constants.AuthUrlConstants.BASE_REDIRECT_TO_PATH
import com.majorik.moviebox.feature.auth.domain.constants.AuthUrlConstants.REDIRECT_URL
import com.majorik.moviebox.MainActivity
import com.majorik.moviebox.R
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.setVisibilityOption
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.moviebox.feature.auth.presentation.ui.about_tmdb.AboutTheMovieDatabaseActivity
import com.majorik.library.base.utils.FontSpan
import com.majorik.moviebox.feature.KoinManager
import kotlinx.android.synthetic.main.activity_first_start.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FirstStartActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModel()
    private val credentialsManager: CredentialsPrefsManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (credentialsManager.getTmdbLoggedStatus()) { startMainActivity() }

        setContentView(com.majorik.moviebox.feature.auth.R.layout.activity_first_start)

        setAnimation()

//        loadKoinModules(KoinManager.koinModules)

        setAboutTmdbTextStyle()
        setClickListener()
        setObservers()
    }

    private fun setAnimation() {
        bg_anim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                logo_title.setVisibilityOption(true)

                if (credentialsManager.getTmdbLoggedStatus() || credentialsManager.getTmdbGuestLoggedStatus()) {
                    lifecycleScope.launch {
                        whenStarted {
                            delay(300)

                            startMainActivity()
                        }
                    }
                } else {
                    btn_about_tmdb.setVisibilityOption(true)
                    login_with_tmdb.setVisibilityOption(true)
                    login_with_guest.setVisibilityOption(true)
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
    }

    override fun onResume() {
        super.onResume()

        val data = intent.data
        val approved: String?

        if (data != null && !TextUtils.isEmpty(data.scheme)) {
            approved = data.getQueryParameter("approved")
            if (approved == "true") {
                requestToken?.let {
                    authViewModel.createSessionToken(it)
                } ?: run {
                    authViewModel.getRequestToken()
                }
            } else {
                requestToken = null
                Toast.makeText(this, "Произошла ошибка, повторите попытку", Toast.LENGTH_LONG)
                    .show()
            }
        }

        requestToken = null
    }

    private fun setObservers() {
        authViewModel.tmdbRequestTokenLiveData.observe(this, Observer {
            requestToken = it.requestToken
            openBrowser(it.requestToken)
        })

        authViewModel.tmdbSessionLiveData.observe(this, Observer {
            credentialsManager.saveTmdbSession(it.success ?: false, it.sessionId)
            if (it.success == true && it.sessionId != null) {
                startMainActivity()
            }
        })

        authViewModel.tmdbGuestSessionLiveData.observe(this, Observer {
            credentialsManager.saveGuestLoginStatus(it.success ?: false)

            if (it.success == true) {
                credentialsManager.saveGuestSessionID(it.requestToken)
                startMainActivity()
            }
        })
    }

    private fun setAboutTmdbTextStyle() {
        val what = getString(R.string.what)
        val tmdb = getString(R.string.the_movie_database)

        val fullText = "$what $tmdb?"
        SpannableStringBuilder(fullText).apply {
            setSpan(
                FontSpan(
                    "cc_montserrat_bold",
                    ResourcesCompat.getFont(
                        this@FirstStartActivity,
                        R.font.cc_montserrat_black
                    )
                ),
                what.length,
                fullText.length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )

            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        this@FirstStartActivity,
                        R.color.caribbean_green_tmdb
                    )
                ),
                what.length,
                fullText.length - 1,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )

            about_tmdb.text = this
        }
    }

    private fun setClickListener() {
        btn_about_tmdb.setSafeOnClickListener {
            startActivityWithAnim(AboutTheMovieDatabaseActivity::class.java)
        }

        login_with_tmdb.setSafeOnClickListener {
            requestToken?.let {
                openBrowser(it)
            } ?: run {
                authViewModel.getRequestToken()
            }
        }

        login_with_guest.setSafeOnClickListener {
            credentialsManager.getTmdbGuestSessionID()?.let {
                startMainActivity()
            } ?: run {
                authViewModel.createGuestSession()
            }
        }
    }

    private fun openBrowser(requestToken: String) {
        val intent = Intent(Intent.ACTION_VIEW)

        intent.data =
            Uri.parse(
                "$BASE_AUTHENTICATE_URL$requestToken$BASE_REDIRECT_TO_PATH$REDIRECT_URL"
            )

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun startMainActivity() {
        finish()
        startActivityWithAnim(MainActivity::class.java)
    }

    companion object {
        private var requestToken: String? = null
    }
}
