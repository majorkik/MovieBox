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
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.tv.TV
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.displayImageWithCenterInside
import com.majorik.moviebox.ui.tvDetails.TVDetailsActivity

class TVCardAdapter(
    private val tvs: List<TV>
) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.item_big_image, container, false)
        val viewPager: ViewPager = container as ViewPager

        bindTo(tvs[position], view)

        viewPager.addView(view, 0)
        return view
    }

    override fun getCount() = tvs.size

    private fun bindTo(tv: TV, parent: View) {
        val sliderImage: ImageView = parent.findViewById(R.id.slider_image)
        val sliderLayout: CardView = parent.findViewById(R.id.slider_layout)


        sliderImage.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_1280 + tv.backdropPath)

        sliderLayout.setOnClickListener {
            val intent = Intent(parent.context, TVDetailsActivity::class.java)

            intent.putExtra("id", tv.id)

            parent.context.startActivity(intent)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

}