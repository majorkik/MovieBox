package com.majorik.moviebox.feature.auth.presentation.ui.authorization

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.extensions.setWindowTransparency
import com.majorik.library.base.extensions.updateMargin
import com.majorik.moviebox.feature.auth.R
import com.majorik.moviebox.feature.auth.databinding.ActivityAuthorizationBinding

class AuthorizationActivity : AppCompatActivity(R.layout.activity_authorization) {
    private val viewBinding: ActivityAuthorizationBinding by viewBinding(R.id.auth_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setWindowTransparency(::updateMargins)
    }

    private fun updateMargins(
        statusBarSize: Int,
        navigationBarSize: Int
    ) {
        viewBinding.splashContainer.updateMargin(bottom = navigationBarSize)
    }

    override fun onResume() {
        super.onResume()

        val data = intent.data
        val approved: String?

        if (data != null && !TextUtils.isEmpty(data.scheme)) {
            approved = data.getQueryParameter("approved")

            sendRequestToken(approved)
        }
    }

    private fun sendRequestToken(approved: String?) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.splash_container) as NavHostFragment

        navHostFragment.navController.navigate(
            R.id.nav_authorization,
            Bundle().apply {
                putString(KEY_APPROVED, approved)
            }
        )
    }

    companion object {
        const val KEY_APPROVED = "key_approved"
    }
}
