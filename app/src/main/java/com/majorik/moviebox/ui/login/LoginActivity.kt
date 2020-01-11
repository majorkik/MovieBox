package com.majorik.moviebox.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.majorik.domain.constants.UrlConstants
import com.majorik.moviebox.R
import com.majorik.moviebox.utils.SharedPrefsManager
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val loginPageViewModel: LoginPageViewModel by viewModel()
    private val sharedPrefs: SharedPrefsManager by inject()

    companion object {
        private var requestToken: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (sharedPrefs.getTmdbLoggedStatus()) {
            Toast.makeText(this, "Вы уже вошли", Toast.LENGTH_SHORT).show()
        }

        setClickListeners()
        setObservers()
    }

    override fun onResume() {
        super.onResume()

        val data = intent.data
        val approved: String?

        if (data != null && !TextUtils.isEmpty(data.scheme)) {
            approved = data.getQueryParameter("approved")
            if (approved == "true") {
                loginPageViewModel.createSessionToken(
                    requestToken
                )
            } else {
                Toast.makeText(this, "Произошла ошибка, повторите попытку", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setClickListeners() {
        btn_trakt_auth.setOnClickListener {
            loginPageViewModel.getRequestToken()
        }
    }

    private fun setObservers() {
        loginPageViewModel.tmdbRequestTokenLiveData.observe(this, Observer {
            requestToken = it.requestToken
            openBrowser()
        })

        loginPageViewModel.tmdbSessionLiveData.observe(this, Observer {
            sharedPrefs.saveTmdbLogged(it)
        })
    }

    private fun openBrowser() {
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
}
