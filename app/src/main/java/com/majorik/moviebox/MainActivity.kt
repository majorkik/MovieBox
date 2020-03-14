package com.majorik.moviebox

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.majorik.domain.constants.AppConfig
import com.majorik.moviebox.extensions.startActivityWithAnim
import com.majorik.moviebox.ui.main_page_movies.MoviesFragment
import com.majorik.moviebox.ui.main_page_profile.ProfileFragment
import com.majorik.moviebox.ui.main_page_search.SearchFragment
import com.majorik.moviebox.ui.main_page_tvs.TVsFragment
import com.majorik.moviebox.ui.search.SearchableActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : LocalizationActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val fragmentMovie = MoviesFragment.newInstance()
    private val fragmentTV = SearchFragment.newInstance()
    private val fragmentDiscover = TVsFragment.newInstance()
    private val fragmentProfile = ProfileFragment.newInstance()
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

    // TODO unused
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.toolbar_search -> {
                startActivityWithAnim(SearchableActivity::class.java)
            }
        }
        return false
    }
}
