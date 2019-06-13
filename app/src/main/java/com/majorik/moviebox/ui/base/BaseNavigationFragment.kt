package com.majorik.moviebox.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.majorik.moviebox.R

abstract class BaseNavigationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(getLayoutId(), container, false)

        setHasOptionsMenu(true)

        return root
    }

    abstract fun getLayoutId(): Int

    abstract fun fetchData()

    abstract fun setObservers()

}