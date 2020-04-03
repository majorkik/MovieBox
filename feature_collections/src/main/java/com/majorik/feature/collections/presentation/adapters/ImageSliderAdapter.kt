package com.majorik.feature.collections.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.majorik.library.base.constants.UrlConstants
import com.majorik.moviebox.R
import com.majorik.library.base.extensions.displayImageWithCenterInside
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.stfalcon.imageviewer.StfalconImageViewer

class ImageSliderAdapter(private val backdropImages: List<String>) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = backdropImages.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.item_big_image, container, false)
        val imageView: ImageView = view.findViewById(R.id.slider_image)
        val viewPager: ViewPager = container as ViewPager

        imageView.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_780 + backdropImages[position])

        viewPager.addView(view, 0)

        imageView.setSafeOnClickListener {
            StfalconImageViewer.Builder(container.context, backdropImages) { view, image ->
                view.displayImageWithCenterInside(UrlConstants.TMDB_BACKDROP_SIZE_1280 + image)
            }.withHiddenStatusBar(false).withStartPosition(position).show()
        }

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}
