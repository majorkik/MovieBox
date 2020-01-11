package com.majorik.moviebox.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tab_collections.*

abstract class BaseTabActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        setSupportActionBar(toolbar_collections)

        toolbar_title.text = getToolbarTitle()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    abstract fun getLayoutId(): Int

    abstract fun getToolbarTitle(): String
}
