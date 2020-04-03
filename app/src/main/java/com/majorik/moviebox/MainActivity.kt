package com.majorik.moviebox

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.majorik.library.base.constants.AppConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : LocalizationActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val fragmentMovie =
        Class.forName("com.majorik.feature.navigation.presentation.main_page_movies.MoviesFragment")
            .newInstance() as Fragment
    private val fragmentTV =
        Class.forName("com.majorik.feature.navigation.presentation.main_page_tvs.TVsFragment").newInstance() as Fragment
    private val fragmentDiscover =
        Class.forName("com.majorik.feature.navigation.presentation.main_page_search.SearchFragment")
            .newInstance() as Fragment
    private val fragmentProfile =
        Class.forName("com.majorik.feature.navigation.presentation.main_page_profile.ProfileFragment")
            .newInstance() as Fragment
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppConfig.REGION = getCurrentLanguage().toString()

        nav_view.setOnNavigationItemSelectedListener(this)

        hideAllFragments()

        nav_view.selectedItemId = R.id.navigation_homepage
//        setSupportActionBar(toolbar)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_homepage -> {
//                toolbar.title = getString(R.string.title_nav_homepage)
                showFragment(fragmentMovie)
                activeFragment = fragmentMovie
                return true
            }
            R.id.navigation_discover -> {
//                toolbar.title = getString(R.string.title_nav_discover)
                showFragment(fragmentDiscover)
                activeFragment = fragmentDiscover
                return true
            }
            R.id.navigation_episodes -> {
//                toolbar.title = getString(R.string.title_nav_episodes)
                showFragment(fragmentTV)
                activeFragment = fragmentTV
                return true
            }
            R.id.navigation_profile -> {
//                toolbar.title = getString(R.string.title_nav_profile)
                showFragment(fragmentProfile)
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

    private fun hideFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.nav_host_fragment, fragment)
            .hide(fragment)
            .commit()
    }

    private fun showFragment(fragment: Fragment) {
        if (activeFragment == null) activeFragment = fragmentMovie

        supportFragmentManager.beginTransaction()
            .hide(activeFragment!!)
            .show(fragment)
            .commit()
    }
}
