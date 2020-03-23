package com.majorik.moviebox.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.tmdbModels.genre.Genre
import com.majorik.domain.tmdbModels.tv.TV
import com.majorik.moviebox.adapters.GenreAdapter.GenreViewHolder
import com.majorik.moviebox.databinding.ItemGenreBinding
import java.util.*
import kotlinx.android.synthetic.main.item_genre.view.*

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

    internal fun addItems(items: List<Genre>) {
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
