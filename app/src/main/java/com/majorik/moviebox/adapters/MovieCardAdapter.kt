package com.majorik.moviebox.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.majorik.domain.UrlConstants
import com.majorik.domain.tmdbModels.movie.MovieResponse
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.displayImageWithCenterInside
import com.majorik.moviebox.ui.movieDetails.MovieDetailsActivity

class MovieCardAdapter(
    private val movies: List<MovieResponse.Movie>
) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.item_big_image, container, false)
        val viewPager: ViewPager = container as ViewPager

        bindTo(movies[position], view)

        viewPager.addView(view, 0)
        return view
    }

    override fun getCount() = movies.size

    private fun bindTo(movie: MovieResponse.Movie, parent: View) {
        val sliderImage: ImageView = parent.findViewById(R.id.slider_image)
        val sliderLayout: CardView = parent.findViewById(R.id.slider_layout)


        sliderImage.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_1280 + movie.backdropPath)

        sliderLayout.setOnClickListener {
            val intent = Intent(parent.context, MovieDetailsActivity::class.java)

            intent.putExtra("id", movie.id)

            parent.context.startActivity(intent)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

}