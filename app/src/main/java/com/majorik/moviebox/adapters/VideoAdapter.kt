package com.majorik.moviebox.adapters

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.majorik.domain.UrlConstants
import com.majorik.domain.models.other.Video
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.displayImageWithCenterInside

class VideoAdapter(
    private val videos: List<Video>
) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.layout_item_video, container, false)
        val viewPager: ViewPager = container as ViewPager

        bindTo(videos[position], view)

        viewPager.addView(view, 0)
        return view
    }

    override fun getCount() = videos.size

    private fun bindTo(video: Video, parent: View) {
        val trailerTitle = parent.findViewById(R.id.trailer_title) as TextView
        val trailerImage = parent.findViewById(R.id.trailer_image) as ImageView
        val trailerCard = parent.findViewById(R.id.trailer_card) as CardView

        trailerTitle.text = video.name

        trailerImage.displayImageWithCenterInside(UrlConstants.YOUTUBE_IMAGE_LINK + video.key + UrlConstants.YOUTUBE_SIZE_MQ)

        trailerCard.setOnClickListener {
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${video.key}"))
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=${video.key}")
            )
            try {
                parent.context.startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                parent.context.startActivity(webIntent)
            }
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

}