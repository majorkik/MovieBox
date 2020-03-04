package com.majorik.moviebox.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.majorik.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.startDetailsActivityWithId
import com.majorik.moviebox.extensions.toDate
import com.majorik.moviebox.ui.movieDetails.MovieDetailsActivity
import com.majorik.moviebox.utils.GenresStorageObject
import kotlin.math.roundToInt
import kotlinx.android.synthetic.main.item_trend_card_with_title.view.*

class MovieTrendAdapter(
    private val movies: List<Movie>
) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View

        if (position < count - 1) {
            view = layoutInflater.inflate(R.layout.item_trend_card_with_title, container, false)
            bindTo(movies[position], view)
        } else {
            view = layoutInflater.inflate(R.layout.item_trend_last_item_card, container, false)
            bindToWhenLastItem(view)
        }

        val viewPager: ViewPager = container as ViewPager

        viewPager.addView(view, 0)

        return view
    }

    override fun getCount() = movies.size + 1

    @SuppressLint("SetTextI18n")
    private fun bindTo(movie: Movie, view: View) {
        view.m_trend_title.text = movie.title

        view.m_trend_year.text = movie.releaseDate?.toDate()?.yearInt?.toString() ?: ""
        view.m_trend_genres.text =
            movie.genreIds.map { GenresStorageObject.movieGenres.get(it) }
                .take(2)
                .joinToString(" - ") { it ?: "" }

        view.m_trend_rating.text = "${(movie.voteAverage * 10).roundToInt()}%"

        view.m_trend_layout.setSafeOnClickListener {
            view.context.startDetailsActivityWithId(
                movie.id,
                MovieDetailsActivity::class.java
            )
        }
    }

    private fun bindToWhenLastItem(view: View) {
        view.m_trend_layout.setSafeOnClickListener {
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}
