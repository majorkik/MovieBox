package com.majorik.library.base.delegates

import android.view.View
import androidx.annotation.RestrictTo
import androidx.viewbinding.ViewBinding

interface ViewBinder<T : ViewBinding> {
    fun bind(view: View): T
}

@PublishedApi
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal inline fun <T : ViewBinding> viewBinder(crossinline bindView: (View) -> T): ViewBinder<T> {
    return object : ViewBinder<T> {
        override fun bind(view: View) = bindView(view)
    }
}
