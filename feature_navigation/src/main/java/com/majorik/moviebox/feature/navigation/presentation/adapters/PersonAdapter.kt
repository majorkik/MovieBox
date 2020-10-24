package com.majorik.moviebox.feature.navigation.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.person.Person
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.displayImageWithCenterInside
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.navigation.databinding.ItemPersonProfileCardBinding
import com.majorik.moviebox.feature.navigation.domain.utils.getPersonDiffUtils

class PersonAdapter(private val clickAction: (id: Int) -> Unit) :
    PagingDataAdapter<Person, PersonAdapter.PersonViewHolder>(getPersonDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonProfileCardBinding.inflate(layoutInflater, parent, false)

        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        getItem(position)?.let { person ->
            holder.bindTo(person)

            holder.viewBinding.personProfileImage.setSafeOnClickListener {
                clickAction(person.id)
            }
        }
    }

    class PersonViewHolder(val viewBinding: ItemPersonProfileCardBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindTo(person: Person) {
            viewBinding.run {
                personName.text = person.name

                personProfileImage.displayImageWithCenterInside(
                    UrlConstants.TMDB_PROFILE_SIZE_185 + person.profilePath
                )
            }
        }
    }
}
