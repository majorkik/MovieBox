package com.majorik.moviebox.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.tmdbModels.genre.Genre
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.GenresInlineAdapter.*
import kotlinx.android.synthetic.main.item_genre_inline.view.*

class GenresInlineAdapter(private val genres: List<Genre>) :
    RecyclerView.Adapter<GenreViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_genre_inline, parent, false)

        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindTo(genres[position])
    }

    class GenreViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindTo(genre: Genre) {
            itemView.genre_name.text = genre.name
        }
    }
}
