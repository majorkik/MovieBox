package com.majorik.moviebox.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.tv.TV
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.displayImageWithCenterInside
import com.majorik.moviebox.extensions.startDetailsActivityWithId
import com.majorik.moviebox.ui.tvDetails.TVDetailsActivity
import kotlinx.android.synthetic.main.item_big_image_with_corners.view.*

class TVCardAdapter(
    private val tvs: List<TV>
) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View =
            layoutInflater.inflate(R.layout.item_big_image_with_corners, container, false)
        val viewPager: ViewPager = container as ViewPager

        bindTo(tvs[position], view)

        viewPager.addView(view, 0)
        return view
    }

    override fun getCount() = tvs.size

    private fun bindTo(tv: TV, parent: View) {
        parent.placeholder_text.text = tv.name

        parent.slider_image.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_1280 + tv.backdropPath)

        parent.slider_layout.setOnClickListener {
            parent.context.startDetailsActivityWithId(
                tv.id,
                TVDetailsActivity::class.java
            )
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}