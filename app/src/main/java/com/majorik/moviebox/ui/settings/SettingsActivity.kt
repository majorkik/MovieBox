package com.majorik.moviebox.ui.settings

import android.app.usage.StorageStatsManager
import android.content.Context
import android.content.pm.PackageStats
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.os.storage.StorageManager
import android.text.format.Formatter
import android.view.View
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.setWindowTransparency
import com.majorik.moviebox.extensions.updateMargin
import com.majorik.moviebox.ui.base.BaseSlidingActivity
import com.majorik.moviebox.ui.settings.clear_dialog.ClearCacheDialog
import com.majorik.moviebox.ui.settings.clear_dialog.ClearDialogListener
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseSlidingActivity(), ClearDialogListener {

    override fun getRootView(): View = window.decorView.rootView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setWindowTransparency(::updateMargins)

        calculateCache()

        setClickListeners()
    }

    private fun updateMargins(statusBarSize: Int, @Suppress("UNUSED_PARAMETER") navigationBarSize: Int) {
        settings_toolbar.updateMargin(top = statusBarSize)
    }

    override fun onSlidingStarted() {}

    override fun onSlidingFinished() {}

    override fun canSlideDown(): Boolean = true

    override fun onResume() {
        super.onResume()

        settings_cache_size.text = Formatter.formatShortFileSize(this, calculateCache())
    }

    override fun onDialogDismiss() {
        settings_cache_size.text = Formatter.formatShortFileSize(this, calculateCache())
    }

    private fun setClickListeners() {
        btn_clear_cache.setSafeOnClickListener {
            ClearCacheDialog.newInstance(settings_cache_size.text.toString())
                .show(supportFragmentManager, "clear_dialog")
        }
    }

    private fun calculateCache(): Long {
        val cacheSize: Long

        if (Build.VERSION.SDK_INT >= 26) {
            val ssm = getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
            val user = Process.myUserHandle()

            val sm = ssm.queryStatsForPackage(StorageManager.UUID_DEFAULT, this.packageName, user)

            cacheSize = sm.cacheBytes
        } else {
            val p = PackageStats(packageName)

            cacheSize = p.cacheSize
        }

        Logger.i("getCacheBytes ${Formatter.formatShortFileSize(this, cacheSize)}")

        return cacheSize
    }
}
