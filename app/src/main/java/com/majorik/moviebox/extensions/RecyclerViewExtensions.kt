package com.majorik.moviebox.extensions

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setAdapterWithFixedSize(
    adapter: RecyclerView.Adapter<*>,
    hasFixedSize: Boolean = false
) {
    this.adapter = adapter
    this.setHasFixedSize(hasFixedSize)
}
