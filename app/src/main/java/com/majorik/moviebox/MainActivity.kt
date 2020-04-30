package com.majorik.moviebox

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.extensions.loadFragmentOrReturnNull
import com.majorik.library.base.utils.PACKAGE_NAME
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : LocalizationActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val fragmentMovie =
        ("$PACKAGE_NAME.feature.navigation.presentation.main_page_movies.MoviesFragment").loadFragmentOrReturnNull()
    private val fragmentTV =
        ("$PACKAGE_NAME.feature.navigation.presentation.main_page_tvs.TVsFragment").loadFragmentOrReturnNull()
    private val fragmentDiscover =
        ("$PACKAGE_NAME.feature.navigation.presentation.main_page_search.SearchFragment").loadFragmentOrReturnNull()
    private val fragmentProfile =
        ("$PACKAGE_NAME.feature.navigation.presentation.main_page_profile.ProfileFragment").loadFragmentOrReturnNull()
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkLoadModules()

        AppConfig.REGION = getCurrentLanguage().toString()

        nav_view.setOnNavigationItemSelectedListener(this)

        hideAllFragments()

        nav_view.selectedItemId = R.id.navigation_movies
//        setSupportActionBar(toolbar)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
        SplitCompat.install(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_movies -> {
                showFragment(fragmentMovie!!)
                activeFragment = fragmentMovie
                return true
            }
            R.id.navigation_tvs -> {
                showFragment(fragmentTV!!)
                activeFragment = fragmentTV
                return true
            }
            R.id.navigation_discover -> {
                showFragment(fragmentDiscover!!)
                activeFragment = fragmentDiscover
                return true
            }
            R.id.navigation_profile -> {
                showFragment(fragmentProfile!!)
                activeFragment = fragmentProfile
                return true
            }
        }
        return false
    }

    override fun onBackPressed() {
        if (nav_view.selectedItemId == R.id.navigation_profile) {
            super.onBackPressed()
        } else {
            nav_view.selectedItemId = R.id.navigation_profile
        }
    }

    private fun hideAllFragments() {
        hideFragment(fragmentMovie)
        hideFragment(fragmentTV)
        hideFragment(fragmentDiscover)
        hideFragment(fragmentProfile)
    }

    private fun hideFragment(fragment: Fragment?) {
        fragment?.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.nav_host_fragment, this)
                .hide(this)
                .commit()
        }
    }

    private fun showFragment(fragment: Fragment?) {
        if (activeFragment == null) activeFragment = fragmentMovie

        fragment?.run {
            supportFragmentManager.beginTransaction()
                .hide(activeFragment!!)
                .show(this)
                .commit()
        }
    }

    private fun checkLoadModules() {
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_collections").toString())
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_details").toString())
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_search").toString())
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_navigation").toString())
        Logger.i(SplitInstallManagerFactory.create(this).installedModules.contains("feature_auth").toString())
    }
}
