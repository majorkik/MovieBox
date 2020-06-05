package com.majorik.library.base.base

import android.os.Bundle
import com.akexorcist.localizationactivity.ui.LocalizationActivity

abstract class BaseTabActivity : LocalizationActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
    }

    abstract fun getLayoutId(): Int
}
