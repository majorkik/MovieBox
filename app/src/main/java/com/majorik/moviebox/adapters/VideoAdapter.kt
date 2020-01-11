package com.majorik.moviebox.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.video.Video
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.displayImageWithCenterInside
import com.majorik.moviebox.extensions.openYouTube

class VideoAdapter(
    private val videos: List<Video>
) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.item_video_card, container, false)
        val viewPager: ViewPager = container as ViewPager

        bindTo(videos[position], view)

        viewPager.addView(view, 0)
        return view
    }

    override fun getCount() = videos.size

    private fun bindTo(video: Video, parent: View) {
        val trailerTitle: TextView = parent.findViewById(R.id.trailer_title)
        val trailerImage: ImageView = parent.findViewById(R.id.trailer_image)
        val trailerCard: CardView = parent.findViewById(R.id.trailer_card)

        trailerTitle.text = video.name

        trailerImage.displayImageWithCenterInside(UrlConstants.YOUTUBE_IMAGE_LINK + video.key + UrlConstants.YOUTUBE_SIZE_MQ)

        trailerCard.setOnClickListener {
            it.context.openYouTube(video.key)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}
