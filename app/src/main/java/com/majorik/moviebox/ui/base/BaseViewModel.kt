package com.majorik.moviebox.ui.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

abstract class BaseViewModel : ViewModel() {
    private val parentJob = SupervisorJob()

    private val uiCoroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val ioCoroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    protected val uiScope = CoroutineScope(uiCoroutineContext)
    protected val ioScope = CoroutineScope(ioCoroutineContext)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onCleared() {
        super.onCleared()

        uiScope.coroutineContext.cancelChildren(null)
        ioScope.coroutineContext.cancelChildren(null)
    }
}
