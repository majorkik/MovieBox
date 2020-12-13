package com.majorik.moviebox.feature.search.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterCrop
import com.majorik.moviebox.feature.search.databinding.ItemPersonDetailedBinding
import com.majorik.moviebox.feature.search.domain.tmdbModels.person.Person
import com.majorik.moviebox.feature.search.R

internal class SearchPeopleViewHolder(val viewBinding: ItemPersonDetailedBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    fun bindTo(item: Person?) {
        item?.let {
            viewBinding.pName.text = it.name
            viewBinding.pImage.displayImageWithCenterCrop(
                UrlConstants.TMDB_PROFILE_SIZE_185 + it.profilePath,
                R.drawable.placholder_profile_image
            )
            viewBinding.pKnowForDepartment.text = it.knowForDepartment
        }
    }
}
