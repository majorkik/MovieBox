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
import com.majorik.domain.tmdbModels.tv.TVResponse
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.ui.tvDetails.TVDetailsActivity

class TVCollectionSliderAdapter(private val tvs: List<TVResponse.TV>) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount() = tvs.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.item_backdrop_card, container, false)
        val viewPager = container as ViewPager

        val backdropImage: ImageView = view.findViewById(R.id.backdrop_image) as ImageView
        val backdropTitle: TextView = view.findViewById(R.id.backdrop_title) as TextView

        backdropImage.displayImageWithCenterCrop(UrlConstants.TMDB_BACKDROP_SIZE_780 + tvs[position].backdropPath)
        backdropTitle.text = tvs[position].name

        view.setOnClickListener {
            val intent = Intent(it.context, TVDetailsActivity::class.java)

            intent.putExtra("id", tvs[position].id)

            it.context.startActivity(intent)
        }

        viewPager.addView(view, 0)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}