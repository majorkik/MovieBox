package com.majorik.moviebox.viewholders

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.startActivityWithAnim
import com.majorik.moviebox.ui.movieDetails.MovieDetailsActivity
import kotlinx.android.synthetic.main.item_medium_poster_card.view.*

class MoviePagedItemVH(val parent: View) : RecyclerView.ViewHolder(parent) {
    fun bindTo(movie: Movie?) {
        movie?.let {
            itemView.title.text = movie.title

            itemView.collection_image.displayImageWithCenterCrop(
                UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath
            )

            itemView.collection_card.setSafeOnClickListener {
                val intent = Intent(parent.context, MovieDetailsActivity::class.java)

                intent.putExtra("id", movie.id)

                parent.context.startActivityWithAnim(intent)
            }
        }
    }
}
