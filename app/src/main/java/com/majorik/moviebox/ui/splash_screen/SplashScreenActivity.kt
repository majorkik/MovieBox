package com.majorik.moviebox.ui.splash_screen

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.extensions.loadIntentOrReturnNull
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.moviebox.R
import com.majorik.moviebox.ui.MainActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashScreenActivity : AppCompatActivity(R.layout.activity_splash_screen) {
    private val credentialsManager: CredentialsPrefsManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAnimation()
    }

    private fun setAnimation() {
        bg_anim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                lifecycleScope.launch {
                    whenStarted {
                        if (credentialsManager.getTmdbLoggedStatus() || credentialsManager.getTmdbGuestLoggedStatus()) {
                            delay(300)

                            startMainActivity()
                        } else {
                            overridePendingTransition(0, 0)
                            startActivity(ScreenLinks.authorization.loadIntentOrReturnNull())
                            finish()
                        }
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationStart(animation: Animator?) {}
        })
    }

    private fun startMainActivity() {
        finish()
        startActivityWithAnim(MainActivity::class.java, animIn = 0, animOut = 0)
    }
}
