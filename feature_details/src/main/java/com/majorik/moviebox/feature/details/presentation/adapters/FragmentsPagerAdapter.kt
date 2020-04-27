package com.majorik.moviebox.feature.details.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.majorik.moviebox.feature.details.presentation.SearchViewTypeChangeListener

class FragmentsPagerAdapter(
    private val fragments: List<Fragment>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = fragments.count()

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun changeViewType(isGridType: Boolean) {
        fragments.forEach {
            (it as? SearchViewTypeChangeListener)?.changeViewType(isGridType)
        }
    }
}
