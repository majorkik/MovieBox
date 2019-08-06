package com.majorik.moviebox.ui.login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import com.majorik.domain.UrlConstants
import com.majorik.moviebox.BuildConfig
import com.majorik.moviebox.R
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginPageViewModel: LoginPageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setClickListeners()
        setObservers()
    }

    override fun onResume() {
        super.onResume()

        val data = intent.data
        val code: String?

        if (data != null && !TextUtils.isEmpty(data.scheme)) {
            code = data.getQueryParameter("code")

            if (!TextUtils.isEmpty(code)) {
                loginPageViewModel.exchangeCodeForAccessToken(
                    code,
                    BuildConfig.TRAKT_API_KEY,
                    BuildConfig.TRAKT_API_SECRET_KEY,
                    "com.majorik.moviebox://redirect"
                )
            }
        }
    }

    private fun setClickListeners() {
        btn_trakt_auth.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse(UrlConstants.TRAKT_AUTH_URL
                        + "response_type=code&client_id="
                        + BuildConfig.TRAKT_API_KEY
                        + "&redirect_uri=com.majorik.moviebox://redirect")

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    private fun setObservers() {
        loginPageViewModel.oauthAccessTokenLiveData.observe(this, Observer {
            login_info.text = it.toString()

            //saveData
        })
    }
}
