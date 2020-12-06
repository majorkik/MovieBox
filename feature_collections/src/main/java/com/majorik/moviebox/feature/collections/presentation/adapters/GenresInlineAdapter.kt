package com.majorik.moviebox.feature.collections.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.collections.domain.tmdbModels.genre.Genre
import com.majorik.moviebox.feature.collections.databinding.ItemGenreInlineBinding
import com.majorik.moviebox.feature.collections.presentation.adapters.GenresInlineAdapter.GenreViewHolder

class GenresInlineAdapter(private val genres: List<Genre>) :
    RecyclerView.Adapter<GenreViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemGenreInlineBinding.inflate(layoutInflater, parent, false)

        return GenreViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindTo(genres[position])
    }

    class GenreViewHolder(val viewBinding: ItemGenreInlineBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bindTo(genre: Genre) {
            viewBinding.genreName.text = genre.name
        }
    }
}
