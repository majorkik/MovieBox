package com.majorik.moviebox.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import com.arlib.floatingsearchview.FloatingSearchView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class DebouncingQueryTextListener(
    lifecycle: Lifecycle,
    private val onDebouncingQueryTextChange: (String) -> Unit
) : FloatingSearchView.OnQueryChangeListener {
    private var debouncePeriod: Long = 500

    private val coroutineScope: CoroutineScope = lifecycle.coroutineScope

    private var searchJob: Job? = null

    override fun onSearchTextChanged(oldQuery: String?, newQuery: String?) {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newQuery?.let {
                delay(debouncePeriod)
                onDebouncingQueryTextChange(newQuery)
            }
        }
    }
}
