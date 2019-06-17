package com.majorik.moviebox.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.majorik.domain.UrlConstants
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.displayImageWithCenterInside
import com.stfalcon.imageviewer.StfalconImageViewer


class ImageSliderAdapter(private val backdropImages: List<String>) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = backdropImages.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.layout_item_big_image, container, false)
        val imageView = view.findViewById(R.id.slider_image) as ImageView
        val viewPager: ViewPager = container as ViewPager

        imageView.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_780 + backdropImages[position])

        viewPager.addView(view, 0)

        imageView.setOnClickListener {
            StfalconImageViewer.Builder<String>(container.context, backdropImages) { view, image ->
                view.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_1280 + image)
            }.withStartPosition(position).show()
        }

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container as ViewPager
        val view = `object` as View
        viewPager.removeView(view)
    }
}