package com.majorik.moviebox.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.tmdbModels.genre.Genre
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.GenreAdapter.GenreViewHolder
import java.util.*
import kotlinx.android.synthetic.main.item_genre.view.*

class GenreAdapter(private val genres: List<Genre>) : RecyclerView.Adapter<GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)

        return GenreViewHolder(view)
    }

    override fun getItemCount() = genres.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindTo(genres[position])
    }

    class GenreViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        @UseExperimental(ExperimentalStdlibApi::class)

        fun bindTo(genre: Genre) {
            itemView.genre_name.text = genre.name.capitalize(Locale.getDefault())
        }
    }
}
