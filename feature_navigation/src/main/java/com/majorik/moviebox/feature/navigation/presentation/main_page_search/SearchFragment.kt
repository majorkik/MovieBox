package com.majorik.moviebox.feature.navigation.presentation.main_page_search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.constants.ScreenLinks
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startActivityWithAnim
import com.majorik.library.base.utils.PACKAGE_NAME
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewBinding: FragmentSearchBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.searchBar.setSafeOnClickListener {
            context?.startActivityWithAnim(ScreenLinks.searchableActivity)
        }

        viewBinding.btnDiscover.setSafeOnClickListener {
            context?.startActivityWithAnim(ScreenLinks.discoverActivity)
        }
    }
}
