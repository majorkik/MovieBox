package com.majorik.moviebox.feature.navigation.presentation.language

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.majorik.library.base.extensions.updateLanguage
import com.majorik.moviebox.feature.navigation.presentation.adapters.LanguageAdapter
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.databinding.FragmentLanguagesBinding

class LanguageFragment : Fragment(R.layout.fragment_languages) {

    private val viewBinding: FragmentLanguagesBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureAdapter()
        setClickListener()
    }

    private fun configureAdapter() {
        viewBinding.listLanguages.adapter = LanguageAdapter {
            (activity as? LocalizationActivity)?.updateLanguage(it)
        }

        viewBinding.listLanguages.setHasFixedSize(true)
    }

    private fun setClickListener() {
        viewBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
