package com.majorik.moviebox.adapters.language

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.AppConfig
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.language.LanguageAdapter.LanguageViewHolder
import com.majorik.moviebox.extensions.setSafeOnClickListener
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.item_lanugage.view.*
import java.util.*

class LanguageAdapter(private val listener: (locale: Locale) -> Unit) :
    RecyclerView.Adapter<LanguageViewHolder>() {

    private val languages: List<Locale> = AppConfig.AVAILABLE_LANGUAGES

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_lanugage, parent, false)

        return LanguageViewHolder(view)
    }

    override fun getItemCount(): Int = AppConfig.AVAILABLE_LANGUAGES.size

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bindTo(languages[position])

        Logger.i(holder.itemView.context.resources.configuration.locale.toLanguageTag())

        holder.itemView.setSafeOnClickListener {
            listener(languages[position])
            notifyDataSetChanged()
        }
    }

    class LanguageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        @UseExperimental(ExperimentalStdlibApi::class)
        fun bindTo(locale: Locale) {
            itemView.lang_name.text = locale.displayName.capitalize(AppConfig.APP_LOCALE)
            if (AppConfig.APP_LOCALE.toLanguageTag() == locale.toLanguageTag()) {
                itemView.language_layout.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.colorAccent
                    )
                )
            } else {
                itemView.language_layout.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.alabaster
                    )
                )
            }
        }
    }
}