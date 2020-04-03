package com.majorik.feature.collections.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.feature.collections.domain.tmdbModels.person.Person
import com.majorik.library.base.constants.UrlConstants
import com.majorik.moviebox.databinding.ItemPersonProfileCardBinding
import com.majorik.library.base.extensions.displayImageWithCenterInside
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.library.base.extensions.startDetailsActivityWithId

class PersonAdapter() : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    private var people: List<Person> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemPersonProfileCardBinding.inflate(layoutInflater, parent, false)

        return PersonViewHolder(binding)
    }

    override fun getItemCount() = people.size

    override fun getItemId(position: Int): Long {
        return people[position].id.toLong()
    }

    fun addItems(items: List<Person>) {
        val startPosition = people.size
        this.people = items
        notifyItemRangeInserted(startPosition, items.size)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bindTo(people[position])
    }

    class PersonViewHolder(private val parent: ItemPersonProfileCardBinding) :
        RecyclerView.ViewHolder(parent.root) {
        fun bindTo(person: Person) {
            parent.personName.text = person.name
            parent.personProfileImage.displayImageWithCenterInside(UrlConstants.TMDB_PROFILE_SIZE_185 + person.profilePath)

            setClickListener(person.id)
        }

        private fun setClickListener(personID: Int) {
            parent.personProfileImage.setSafeOnClickListener {
                //TODO
//                itemView.context.startDetailsActivityWithId(
//                    personID,
//                    PersonDetailsActivity::class.java
//                )
            }
        }
    }
}
