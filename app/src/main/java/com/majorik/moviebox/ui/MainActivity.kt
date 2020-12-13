package com.majorik.moviebox.ui

import android.os.Bundle
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.dynamicfeatures.fragment.DynamicNavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.extensions.*
import com.majorik.moviebox.R
import com.majorik.moviebox.databinding.ActivityMainBinding
import com.orhanobut.logger.Logger
import kotlinx.coroutines.delay
import java.lang.RuntimeException

class MainActivity : LocalizationActivity() {
    private val viewBinding: ActivityMainBinding by viewBinding(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkLoadModules()

        updateMargins()
        updateBottomNavPaddings()

        AppConfig.REGION = getCurrentLanguage().toString()

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    private fun updateMargins() {
        viewBinding.root.doOnApplyWindowInsets { _, insets, _ ->
            viewBinding.cardNavView.updateMargin(bottom = insets.systemWindowInsetBottom.getOrDefault(16.px()))
            viewBinding.mainFragmentContainer.updateMargin(top = insets.systemWindowInsetTop)

            insets
        }
    }

    private fun updateBottomNavPaddings() {
        viewBinding.navView.doOnApplyWindowInsets { view, insets, _ ->
            view.updatePadding(bottom = 0)
            insets
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navHost = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as DynamicNavHostFragment

        val controller = navHost.navController

        viewBinding.navView.setupWithNavController(navHost.navController)

        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                in getNavigationIdsForBottomNavVisible() -> {
                    viewBinding.cardNavView.setVisibilityOption(true)
                }

                else -> {
                    viewBinding.cardNavView.setVisibilityOption(false)
                }
            }
        }

        controller.removeOnDestinationChangedListener(listener)
        controller.addOnDestinationChangedListener(listener)
    }

    private fun getNavigationIdsForBottomNavVisible() = listOf(
        R.id.nav_movies_id,
        R.id.nav_tvs_id,
        R.id.nav_discover_id,
        R.id.nav_profile_id
    )

    /**
     * Проверяем все ли модули успешно подключены (только для логов)
     */

    private fun checkLoadModules() {
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_collections").toString())
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_details").toString())
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_search").toString())
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_navigation").toString())
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_auth").toString())
    }
}
