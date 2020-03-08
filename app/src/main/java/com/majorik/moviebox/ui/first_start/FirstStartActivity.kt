package com.majorik.moviebox.ui.first_start

import android.animation.Animator
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
import com.majorik.domain.constants.UrlConstants
import com.majorik.moviebox.MainActivity
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.setVisibilityOption
import com.majorik.moviebox.extensions.startActivityWithAnim
import com.majorik.moviebox.storage.CredentialsPrefsManager
import com.majorik.moviebox.ui.about_tmdb.AboutTheMovieDatabaseActivity
import com.majorik.moviebox.utils.FontSpan
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_first_start.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class FirstStartActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModel()
    private val credentialsManager: CredentialsPrefsManager by inject()

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (credentialsManager.getTmdbLoggedStatus()) { startMainActivity() }

        setContentView(R.layout.activity_first_start)

        setAboutTmdbTextStyle()
        setAnimation()
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
                    activityScope.launch {
                        delay(300)

                        startMainActivity()
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

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
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
                        R.font.cc_montserrat_bold
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
                "https://www.themoviedb.org/authenticate/" + requestToken + "?redirect_to=" +
                        UrlConstants.REDIRECT_URL
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
