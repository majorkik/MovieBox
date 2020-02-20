package com.majorik.moviebox.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.cast.Cast
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.CastAdapter.CastViewHolder
import com.majorik.moviebox.extensions.displayImageWithCenterInside
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.extensions.startDetailsActivityWithId
import com.majorik.moviebox.ui.person_details.PersonDetailsActivity
import kotlinx.android.synthetic.main.item_cast_profile_card.view.*

class CastAdapter(private val people: List<Cast>) :
    RecyclerView.Adapter<CastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cast_profile_card, parent, false)

        return CastViewHolder(view)
    }

    override fun getItemCount() = people.size

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bindTo(people[position])
    }

    class CastViewHolder(private val parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(cast: Cast) {
            itemView.person_name.text = cast.name
            itemView.character_name.text = cast.character
            itemView.person_profile_image.displayImageWithCenterInside(UrlConstants.TMDB_PROFILE_SIZE_185 + cast.profilePath)

            setClickListener(cast.id)
        }

        private fun setClickListener(castID: Int) {
            itemView.person_profile_image.setSafeOnClickListener {
                parent.context.startDetailsActivityWithId(
                    castID,
                    PersonDetailsActivity::class.java
                )
            }
        }
    }
}
