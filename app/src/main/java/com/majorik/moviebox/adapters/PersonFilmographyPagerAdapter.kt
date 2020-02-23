package com.majorik.moviebox.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PersonFilmographyPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private var fragments: MutableList<Fragment> = mutableListOf()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    fun updateViewType(isGrid: Boolean) {
        fragments.forEach {
            (it as? ChangeViewTypeListener)?.viewTypeChanged(isGrid)
        }
    }
}