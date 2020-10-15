package com.majorik.moviebox.ui

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.dynamicfeatures.fragment.DynamicNavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.extensions.dp
import com.majorik.library.base.extensions.setWindowTransparency
import com.majorik.library.base.extensions.updateMargin
import com.majorik.moviebox.R
import com.majorik.moviebox.databinding.ActivityMainBinding
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : LocalizationActivity() {
    private val viewBinding: ActivityMainBinding by viewBinding(R.id.container)

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setWindowTransparency(::updateMargins)

        checkLoadModules()

        AppConfig.REGION = getCurrentLanguage().toString()

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    private fun updateMargins(@Suppress("UNUSED_PARAMETER") statusBarSize: Int, navigationBarSize: Int) {
        viewBinding.cardNavView.updateMargin(bottom = navigationBarSize + 16.dp())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navHost = supportFragmentManager.findFragmentById(R.id.splash_container) as DynamicNavHostFragment

        nav_view.setupWithNavController(navHost.navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

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
