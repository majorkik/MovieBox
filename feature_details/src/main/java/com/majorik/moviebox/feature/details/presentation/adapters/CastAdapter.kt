package com.majorik.moviebox.feature.details.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.details.domain.tmdbModels.cast.Cast
import com.majorik.moviebox.feature.details.presentation.adapters.CastAdapter.CastViewHolder
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterInside
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId
import com.majorik.moviebox.feature.details.presentation.person_details.PersonDetailsActivity
import kotlinx.android.synthetic.main.item_cast_profile_card_details.view.*

class CastAdapter(private val people: List<Cast>) :
    RecyclerView.Adapter<CastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.majorik.moviebox.feature.details.R.layout.item_cast_profile_card_details, parent, false)

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
