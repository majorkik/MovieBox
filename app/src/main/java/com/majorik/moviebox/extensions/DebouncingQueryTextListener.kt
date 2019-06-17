package com.majorik.moviebox.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.arlib.floatingsearchview.FloatingSearchView
import kotlinx.coroutines.*

internal class DebouncingQueryTextListener(
    lifecycle: Lifecycle,
    private val onDebouncingQueryTextChange: (String) -> Unit
) : FloatingSearchView.OnQueryChangeListener, LifecycleObserver {
    private var debouncePeriod: Long = 500

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private var searchJob: Job? = null

    init {
        lifecycle.addObserver(this)
    }

    override fun onSearchTextChanged(oldQuery: String?, newQuery: String?) {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newQuery?.let {
                delay(debouncePeriod)
                onDebouncingQueryTextChange(newQuery)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun destroy() {
        searchJob?.cancel()
    }
}