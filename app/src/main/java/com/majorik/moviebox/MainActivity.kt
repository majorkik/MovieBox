package com.majorik.moviebox

import android.app.usage.StorageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageStats
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.os.storage.StorageManager
import android.text.format.Formatter
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.majorik.moviebox.ui.main_page_movies.MoviesFragment
import com.majorik.moviebox.ui.main_page_profile.ProfileFragment
import com.majorik.moviebox.ui.main_page_search.SearchFragment
import com.majorik.moviebox.ui.main_page_tvs.TVsFragment
import com.majorik.moviebox.ui.search.SearchableActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val fragmentMovie = MoviesFragment.newInstance()
    private val fragmentTV = SearchFragment.newInstance()
    private val fragmentDiscover = TVsFragment.newInstance()
    private val fragmentProfile = ProfileFragment.newInstance()
    private var activeFragment: Fragment = fragmentMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logCacheSize()

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
                logCacheSize()
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
        supportFragmentManager.beginTransaction()
            .hide(activeFragment)
            .show(fragment)
            .commit()
    }

    // TODO unused
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.toolbar_search -> {
                startActivity(Intent(this, SearchableActivity::class.java))
            }
        }
        return false
    }

    private fun logCacheSize() {
        if (Build.VERSION.SDK_INT >= 26) {

            val ssm = getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
            val user = Process.myUserHandle()

            val sm = ssm.queryStatsForPackage(StorageManager.UUID_DEFAULT, this.packageName, user)

            Logger.i("getAppBytes ${Formatter.formatShortFileSize(this, sm.appBytes)}")
            Logger.i("getCacheBytes ${Formatter.formatShortFileSize(this, sm.cacheBytes)}")
            Logger.i("getDataBytes ${Formatter.formatShortFileSize(this, sm.dataBytes)}")
        } else {
            val p = PackageStats(packageName)
            Logger.i("getCacheBytes ${Formatter.formatShortFileSize(this, p.cacheSize)}")
            Logger.i("getDataBytes ${Formatter.formatShortFileSize(this, p.dataSize)}")
        }
    }
}
