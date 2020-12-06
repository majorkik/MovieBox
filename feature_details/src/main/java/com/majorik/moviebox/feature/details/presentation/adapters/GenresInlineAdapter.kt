package com.majorik.moviebox.feature.details.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.details.databinding.ItemGenreInlineDetailsBinding
import com.majorik.moviebox.feature.details.domain.tmdbModels.genre.Genre
import com.majorik.moviebox.feature.details.presentation.adapters.GenresInlineAdapter.*

class GenresInlineAdapter(private val genres: List<Genre>) :
    RecyclerView.Adapter<GenreViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemGenreInlineDetailsBinding.inflate(layoutInflater, parent, false)

        return GenreViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindTo(genres[position])
    }

    class GenreViewHolder(val viewBinding: ItemGenreInlineDetailsBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bindTo(genre: Genre) {
            viewBinding.genreName.text = genre.name
        }
    }
}
