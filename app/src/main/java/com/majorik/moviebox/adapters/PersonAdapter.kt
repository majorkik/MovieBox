package com.majorik.moviebox.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.UrlConstants
import com.majorik.domain.models.Cast
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.PersonAdapter.PersonViewHolder
import com.majorik.moviebox.extensions.displayImageWithCenterInside
import kotlinx.android.synthetic.main.layout_item_person.view.*

class PersonAdapter(private val people: List<Cast>) :
    RecyclerView.Adapter<PersonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_person, parent, false)

        return PersonViewHolder(view)
    }

    override fun getItemCount() = people.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bindTo(people[position])
    }

    class PersonViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(person: Cast) {
            itemView.person_name.text = person.name
            itemView.character_name.text = person.character

            itemView.person_profile_image.displayImageWithCenterInside(UrlConstants.TMDB_PROFILE_SIZE_185 + person.profilePath)
        }

    }

}