package com.majorik.feature.search.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.majorik.feature.search.domain.tmdbModels.person.Person
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.R
import com.majorik.moviebox.databinding.ItemPersonDetailedBinding

class SearchPeopleViewHolder(val parent: ItemPersonDetailedBinding) : RecyclerView.ViewHolder(parent.root) {
    fun bindTo(item: Person?) {
        item?.let {
            parent.pName.text = it.name
            parent.pImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_PROFILE_SIZE_185 + it.profilePath,
                R.drawable.placholder_profile_image
            )
            parent.pKnowForDepartment.text = it.knowForDepartment
        }
    }
}
