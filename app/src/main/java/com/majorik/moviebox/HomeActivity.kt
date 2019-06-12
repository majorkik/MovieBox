package com.majorik.moviebox

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.majorik.moviebox.ui.movie.MovieFragment
import com.majorik.moviebox.ui.profile.ProfileFragment
import com.majorik.moviebox.ui.search.SearchFragment
import com.majorik.moviebox.ui.tv.TVFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val fragmentMovie = MovieFragment()
    private val fragmentProfile = ProfileFragment()
    private val fragmentSearch = SearchFragment()
    private val fragmentTV = TVFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        nav_view.setOnNavigationItemSelectedListener(this)


        hideAllFragments()

        nav_view.selectedItemId = R.id.navigation_profile
        setSupportActionBar(toolbar)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_movie -> {
                toolbar.title = getString(R.string.title_nav_movie)
                showFragment(fragmentMovie)
                activeFragment = fragmentMovie
                return true
            }
            R.id.navigation_search -> {
                toolbar.title = getString(R.string.title_nav_search)
                showFragment(fragmentSearch)
                activeFragment = fragmentSearch
                return true
            }
            R.id.navigation_tv -> {
                toolbar.title = getString(R.string.title_nav_tv)
                showFragment(fragmentTV)
                activeFragment = fragmentTV
                return true
            }
            R.id.navigation_profile -> {
                toolbar.title = getString(R.string.title_nav_profile)
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
        hideFragment(fragmentSearch)
        hideFragment(fragmentTV)
        hideFragment(fragmentMovie)
        hideFragment(fragmentProfile)
    }

    private fun hideFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, fragment).hide(fragment)
            .commit()
    }

    private fun showFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().hide(activeFragment).show(fragment).commit()
    }
}
