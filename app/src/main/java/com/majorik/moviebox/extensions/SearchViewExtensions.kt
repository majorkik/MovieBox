package com.majorik.moviebox.extensions

import androidx.lifecycle.Lifecycle
import com.arlib.floatingsearchview.FloatingSearchView
import com.majorik.moviebox.utils.DebouncingQueryTextListener
import timber.log.Timber

fun FloatingSearchView.onQueryTextChange(lifecycle: Lifecycle, action: (String) -> Unit) {
    this.setOnQueryChangeListener(
        DebouncingQueryTextListener(lifecycle) { newText ->
            newText.let {
                action.invoke(newText)
                Timber.i(newText)
            }
        }
    )
}