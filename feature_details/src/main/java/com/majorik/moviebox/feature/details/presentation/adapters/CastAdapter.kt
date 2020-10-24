package com.majorik.moviebox.feature.details.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.details.domain.tmdbModels.cast.Cast
import com.majorik.moviebox.feature.details.presentation.adapters.CastAdapter.CastViewHolder
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterInside
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.details.databinding.ItemCastProfileCardDetailsBinding
import kotlinx.android.synthetic.main.item_cast_profile_card_details.view.*

class CastAdapter(private val actionClick: (id: Int) -> Unit, private val people: List<Cast>) :
    RecyclerView.Adapter<CastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemCastProfileCardDetailsBinding.inflate(layoutInflater, parent, false)

        return CastViewHolder(viewBinding)
    }

    override fun getItemCount() = people.size

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bindTo(people[position])

        holder.viewBinding.personProfileImage.setSafeOnClickListener {
            actionClick(people[position].id)
        }
    }

    class CastViewHolder(val viewBinding: ItemCastProfileCardDetailsBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bindTo(cast: Cast) {
            viewBinding.personName.text = cast.name
            viewBinding.characterName.text = cast.character
            viewBinding.personProfileImage.displayImageWithCenterInside(UrlConstants.TMDB_PROFILE_SIZE_185 + cast.profilePath)
        }
    }
}
