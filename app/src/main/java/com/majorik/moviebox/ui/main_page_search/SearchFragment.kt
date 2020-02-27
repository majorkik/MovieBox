package com.majorik.moviebox.ui.main_page_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.startActivityWithAnim
import com.majorik.moviebox.ui.search.SearchableActivity
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_bar.setOnClickListener {
            context?.startActivityWithAnim(SearchableActivity::class.java)
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
