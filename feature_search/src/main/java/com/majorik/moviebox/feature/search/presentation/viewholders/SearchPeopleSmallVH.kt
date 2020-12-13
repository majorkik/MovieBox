package com.majorik.moviebox.feature.search.presentation.viewholders

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.majorik.library.base.constants.UrlConstants
import com.majorik.moviebox.feature.search.databinding.ItemPersonSmallCardBinding
import com.majorik.moviebox.feature.search.domain.tmdbModels.person.Person
import com.majorik.moviebox.feature.search.R

internal class SearchPeopleSmallVH(val viewBinding: ItemPersonSmallCardBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    fun bindTo(item: Person?) {
        item?.let {
            viewBinding.pImage.load(UrlConstants.TMDB_PROFILE_SIZE_185 + it.profilePath) {
                error(R.drawable.placholder_profile_image)
                placeholder(R.drawable.placholder_profile_image)
            }
        }
    }
}
