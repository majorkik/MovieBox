package com.majorik.moviebox.feature.details.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.details.domain.tmdbModels.genre.Genre
import com.majorik.moviebox.feature.details.presentation.adapters.GenreAdapter.GenreViewHolder
import com.majorik.moviebox.databinding.ItemGenreBinding
import java.util.*

class GenreAdapter() : RecyclerView.Adapter<GenreViewHolder>() {

    private val genres: MutableList<Genre> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGenreBinding.inflate(layoutInflater, parent, false)

        return GenreViewHolder(binding)
    }

    override fun getItemCount() = genres.size

    override fun getItemId(position: Int): Long {
        return genres[position].id.toLong()
    }

    fun addItems(items: List<Genre>) {
        items.forEach {
            addItem(it)
        }
    }

    private fun addItem(item: Genre) {
        val startedPosition = genres.size
        genres.add(item)
        notifyItemInserted(startedPosition)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindTo(genres[position])
    }

    class GenreViewHolder(private val parent: ItemGenreBinding) :
        RecyclerView.ViewHolder(parent.root) {

        @UseExperimental(ExperimentalStdlibApi::class)
        fun bindTo(genre: Genre) {
            parent.genreName.text = genre.name.capitalize(Locale.getDefault())
        }
    }
}
