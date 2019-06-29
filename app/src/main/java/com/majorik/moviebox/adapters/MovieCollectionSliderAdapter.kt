package com.majorik.moviebox.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.majorik.domain.UrlConstants
import com.majorik.domain.models.movie.MovieResponse
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.ui.movieDetails.MovieDetailsActivity

class MovieCollectionSliderAdapter(private val movies: List<MovieResponse.Movie>) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount() = movies.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.item_backdrop_card, container, false)
        val viewPager: ViewPager = container as ViewPager

        val backdropImage = view.findViewById(R.id.backdrop_image) as ImageView
        val backdropTitle = view.findViewById(R.id.backdrop_title) as TextView

        backdropImage.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_780 + movies[position].backdropPath)
        backdropTitle.text = movies[position].title
        view.setOnClickListener {
            val intent = Intent(it.context, MovieDetailsActivity::class.java)

            intent.putExtra("id", movies[position].id)

            it.context.startActivity(intent)
        }

        viewPager.addView(view, 0)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}