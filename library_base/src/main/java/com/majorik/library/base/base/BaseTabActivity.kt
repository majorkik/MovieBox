package com.majorik.library.base.base

import android.os.Bundle
import com.akexorcist.localizationactivity.ui.LocalizationActivity

abstract class BaseTabActivity : LocalizationActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

//        setSupportActionBar(toolbar_collections)
//
//        toolbar_title.text = getToolbarTitle()
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    abstract fun getLayoutId(): Int

    abstract fun getToolbarTitle(): String
}
