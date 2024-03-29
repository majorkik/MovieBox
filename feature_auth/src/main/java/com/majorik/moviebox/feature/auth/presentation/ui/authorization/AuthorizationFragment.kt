package com.majorik.moviebox.feature.auth.presentation.ui.authorization

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.setVisibilityOption
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.library.base.utils.FontSpan
import com.majorik.moviebox.feature.auth.R
import com.majorik.moviebox.feature.auth.databinding.FragmentAuthorizationBinding
import com.majorik.moviebox.feature.auth.domain.constants.AuthUrlConstants.BASE_AUTHENTICATE_URL
import com.majorik.moviebox.feature.auth.domain.constants.AuthUrlConstants.BASE_REDIRECT_TO_PATH
import com.majorik.moviebox.feature.auth.domain.constants.AuthUrlConstants.REDIRECT_URL
import com.majorik.moviebox.feature.auth.presentation.ui.about_tmdb.AboutTheMovieDatabaseActivity
import com.majorik.moviebox.feature.auth.presentation.ui.authorization.AuthorizationActivity.Companion.KEY_APPROVED
import com.majorik.moviebox.ui.MainActivity
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.majorik.moviebox.R as AppResources

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {
    private val viewBinding: FragmentAuthorizationBinding by viewBinding(createMethod = CreateMethod.BIND)

    private val viewModel: AuthorizationViewModel by viewModel()

    private val credentialsManager: CredentialsPrefsManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAboutTmdbTextStyle()
        setApplicationTitleStyle()
        setClickListener()
        setObservers()
    }

    override fun onResume() {
        super.onResume()

        viewBinding.applicationTitle.setVisibilityOption(true)
        viewBinding.btnAboutTmdb.setVisibilityOption(true)
        viewBinding.loginWithTmdb.setVisibilityOption(true)
        viewBinding.loginWithGuest.setVisibilityOption(true)

        requestTokenApproved()
    }

    private fun requestTokenApproved() {
        when (arguments?.getString(KEY_APPROVED)) {
            "true" -> {
                requestToken?.let {
                    Logger.d("requestToken = $it")

                    viewModel.createSessionToken(it)
                } ?: run {
                    viewModel.getRequestToken()
                }
            }

            null -> {
            }

            else -> {
                requestToken = null

                Toast.makeText(context, "Произошла ошибка, повторите попытку", Toast.LENGTH_LONG)
                    .show()
            }
        }

        requestToken = null
    }

    private fun setObservers() {
        viewModel.tmdbRequestTokenLiveData.observe(
            viewLifecycleOwner,
            {
                requestToken = it.requestToken
                openBrowser(it.requestToken)
            }
        )

        viewModel.tmdbSessionLiveData.observe(
            viewLifecycleOwner,
            {
                credentialsManager.saveTmdbSession(it.success ?: false, it.sessionId)
                if (it.success == true && it.sessionId != null) {
                    startMainActivity()
                }
            }
        )

        viewModel.tmdbGuestSessionLiveData.observe(
            viewLifecycleOwner,
            {
                credentialsManager.saveGuestLoginStatus(it.success ?: false)

                if (it.success == true) {
                    credentialsManager.saveGuestSessionID(it.requestToken)
                    startMainActivity()
                }
            }
        )
    }

    private fun setAboutTmdbTextStyle() {
        val what = getString(R.string.nav_what)
        val tmdb = getString(R.string.nav_the_movie_database)

        val fullText = "$what $tmdb?"

        SpannableStringBuilder(fullText).apply {
            setSpan(
                FontSpan(
                    "cc_montserrat_bold",
                    ResourcesCompat.getFont(
                        requireContext(),
                        AppResources.font.cc_montserrat_black
                    )
                ),
                what.length,
                fullText.length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )

            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        AppResources.color.caribbean_green_tmdb
                    )
                ),
                what.length,
                fullText.length - 1,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )

            viewBinding.aboutTmdb.text = this
        }
    }

    private fun setApplicationTitleStyle() {
        val firstTextHalf = getString(R.string.nav_app_logo_movie)
        val secondTextHalf = getString(R.string.nav_app_logo_box)

        SpannableStringBuilder("$firstTextHalf$secondTextHalf").apply {
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        AppResources.color.sunglow
                    )
                ),
                firstTextHalf.length,
                (firstTextHalf + secondTextHalf).length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )

            viewBinding.applicationTitle.text = this
        }
    }

    private fun setClickListener() {
        viewBinding.btnAboutTmdb.setSafeOnClickListener {
            context?.startActivityWithAnim(AboutTheMovieDatabaseActivity::class.java)
        }

        viewBinding.loginWithTmdb.setSafeOnClickListener {
            requestToken?.let {
                openBrowser(it)
            } ?: run {
                viewModel.getRequestToken()
            }
        }

        viewBinding.loginWithGuest.setSafeOnClickListener {
            credentialsManager.getTmdbGuestSessionID()?.let {
                startMainActivity()
            } ?: run {
                viewModel.createGuestSession()
            }
        }
    }

    private fun openBrowser(requestToken: String) {
        val intent = Intent(Intent.ACTION_VIEW)

        intent.data =
            Uri.parse(
                "$BASE_AUTHENTICATE_URL$requestToken$BASE_REDIRECT_TO_PATH$REDIRECT_URL"
            )

        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun startMainActivity() {
        activity?.finish()
        context?.startActivityWithAnim(MainActivity::class.java, animIn = 0, animOut = 0)
    }

    companion object {
        private var requestToken: String? = null
    }
}
