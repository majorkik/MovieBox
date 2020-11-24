package com.majorik.moviebox.feature.navigation.presentation.settings

import android.app.usage.StorageStatsManager
import android.content.Context
import android.content.pm.PackageStats
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.os.storage.StorageManager
import android.text.format.Formatter
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.majorik.library.base.extensions.getCurrentLocale
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.updateMargin
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.databinding.FragmentSettingsBinding
import com.majorik.moviebox.feature.navigation.presentation.settings.clear_dialog.ClearCacheDialog
import com.majorik.moviebox.feature.navigation.presentation.settings.clear_dialog.ClearDialogListener
import com.orhanobut.logger.Logger

class SettingsFragment : Fragment(R.layout.fragment_settings), ClearDialogListener {

    private val viewBinding: FragmentSettingsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calculateCache()

        setClickListeners()
        setCurrentLanguage()
    }

    override fun onResume() {
        super.onResume()

        viewBinding.settingsCacheSize.text = Formatter.formatShortFileSize(context, calculateCache())
    }

    override fun onDialogDismiss() {
        viewBinding.settingsCacheSize.text = Formatter.formatShortFileSize(context, calculateCache())
    }

    private fun setClickListeners() {
        viewBinding.btnClearCache.setSafeOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionToClearCache(viewBinding.settingsCacheSize.text.toString()))
        }

        viewBinding.btnChangeLang.setSafeOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionToChangeLanguage())
        }

        viewBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun setCurrentLanguage() {
        val currentLanguage = (activity as? LocalizationActivity)?.getCurrentLanguage() ?: context?.getCurrentLocale()
        viewBinding.tvCurrentLanguage.text =
            currentLanguage?.getDisplayName(currentLanguage)?.capitalize(currentLanguage) ?: ""
    }

    private fun calculateCache(): Long {
        if (activity == null) return 0L

        val cacheSize: Long

        cacheSize = if (Build.VERSION.SDK_INT >= 26) {
            val ssm = requireActivity().getSystemService(Context.STORAGE_STATS_SERVICE) as? StorageStatsManager
            val user = Process.myUserHandle()

            val sm = ssm?.queryStatsForPackage(StorageManager.UUID_DEFAULT, requireActivity().packageName, user)

            sm?.cacheBytes ?: 0L
        } else {
            val p = PackageStats(requireActivity().packageName)

            p.cacheSize
        }

        Logger.i("getCacheBytes ${Formatter.formatShortFileSize(context, cacheSize)}")

        return cacheSize
    }
}
