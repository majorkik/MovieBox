package com.majorik.moviebox.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.UrlConstants
import com.majorik.domain.models.genre.Genre
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.SelectableGenresAdapter.SelectableGenreViewHolder
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.extensions.displayImageWithCenterInside
import kotlinx.android.synthetic.main.item_genre.view.*
import timber.log.Timber

class SelectableGenresAdapter(private val genres: List<Genre>) :
    RecyclerView.Adapter<SelectableGenreViewHolder>() {
    private var selectedGenres: MutableList<Int> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectableGenreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_genre, parent, false)

        return SelectableGenreViewHolder(view)
    }

    override fun getItemCount() = genres.size

    override fun onBindViewHolder(holder: SelectableGenreViewHolder, position: Int) {
        val genre = genres[position]
        val selected = selectedGenres.contains(genre.id)
        holder.bindTo(genre, selected)
        holder.itemView.card_genre.setOnClickListener {
            val nowSelected = selectedGenres.contains(genre.id)
            if (!nowSelected) {
                selectedGenres.add(genre.id)
            } else {
                selectedGenres.remove(genre.id)
            }
            holder.click(selectedGenres.contains(genre.id))
            Timber.i(selectedGenres.toString())
        }
    }

    fun getSelectedGenres(): List<Int> = selectedGenres

    class SelectableGenreViewHolder(private val parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(genre: Genre, selected: Boolean) {
            itemView.genre_image.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_300 + genre.imagePath)
            itemView.genre_title.text = genre.name
            click(selected)
        }

        fun click(selected: Boolean) {
            if (selected) {
                itemView.genre_title.setBackgroundResource(R.color.colorAccent)
            } else {
                itemView.genre_title.setBackgroundResource(R.color.colorPrimaryTransparent)
            }
        }
    }
}