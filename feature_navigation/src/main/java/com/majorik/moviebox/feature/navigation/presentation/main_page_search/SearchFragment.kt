package com.majorik.moviebox.feature.navigation.presentation.main_page_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.majorik.library.base.extensions.startActivityWithAnim
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.majorik.moviebox.feature.navigation.R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_bar.setOnClickListener {
            context?.startActivityWithAnim("com.majorik.moviebox.feature.search.presentation.search.SearchableActivity")
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
