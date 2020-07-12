package com.majorik.moviebox.ui

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.ScreenLinks.discoverFiltersDialog
import com.majorik.library.base.extensions.loadFragmentOrReturnNull
import com.majorik.library.base.extensions.setupWithNavController
import com.majorik.moviebox.R
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : LocalizationActivity() {
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkLoadModules()

        AppConfig.REGION = getCurrentLanguage().toString()

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds =
            listOf(
                R.navigation.navigation_movies,
                R.navigation.navigation_tvs,
                R.navigation.navigation_discover,
                R.navigation.navigation_profile
            )

        val controller = nav_view.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.splash_container,
            intent = intent
        )

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun checkLoadModules() {
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_collections").toString())
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_details").toString())
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_search").toString())
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_navigation").toString())
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_auth").toString())
    }
}
