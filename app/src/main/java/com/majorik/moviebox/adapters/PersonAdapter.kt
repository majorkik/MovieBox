package com.majorik.moviebox.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.cast.Cast
import com.majorik.domain.tmdbModels.person.Person
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.displayImageWithCenterInside
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.majorik.moviebox.ui.person.PersonDetailsActivity
import kotlinx.android.synthetic.main.item_person_profile_card.view.*

class PersonAdapter(private val people: List<Person>) :
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person_profile_card, parent, false)

        return PersonViewHolder(view)
    }

    override fun getItemCount() = people.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bindTo(people[position])
    }

    class PersonViewHolder(private val parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(person: Person) {
            itemView.person_name.text = person.name
            itemView.person_profile_image.displayImageWithCenterInside(UrlConstants.TMDB_PROFILE_SIZE_185 + person.profilePath)

            setClickListener(person.id)
        }

        private fun setClickListener(personID: Int) {
            itemView.person_profile_image.setSafeOnClickListener {
                val intent = Intent(parent.context, PersonDetailsActivity::class.java)

                intent.putExtra("id", personID)

                parent.context.startActivity(intent)
            }
        }
    }
}
