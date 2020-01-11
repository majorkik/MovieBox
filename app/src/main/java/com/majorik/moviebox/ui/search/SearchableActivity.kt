package com.majorik.moviebox.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.majorik.moviebox.R

class SearchableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)

        configureAndShowFragment()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun configureAndShowFragment() {
        var fragment =
            supportFragmentManager.findFragmentById(R.id.searchable_container) as SearchableFragment?
        if (fragment == null) {
            fragment = SearchableFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.searchable_container, fragment)
                .commit()
        }
    }
}
