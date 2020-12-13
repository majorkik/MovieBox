package com.majorik.moviebox.feature.navigation.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.navigation.databinding.ItemGenreBinding
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.genre.Genre
import java.util.*

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

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

    class GenreViewHolder(private val viewBinding: ItemGenreBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindTo(genre: Genre) {
            viewBinding.genreName.text = genre.name.capitalize(Locale.getDefault())
        }
    }
}
