package com.majorik.moviebox.feature.navigation.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.navigation.presentation.adapters.LanguageAdapter.LanguageViewHolder
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.extensions.setSafeOnClickListener
import com.majorik.moviebox.feature.navigation.databinding.ItemLanguageBinding
import java.util.*

class LanguageAdapter(private val listener: (locale: Locale) -> Unit) :
    RecyclerView.Adapter<LanguageViewHolder>() {

    private val languages: List<Locale> = AppConfig.AVAILABLE_LANGUAGES

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemLanguageBinding.inflate(layoutInflater, parent, false)

        return LanguageViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = AppConfig.AVAILABLE_LANGUAGES.size

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bindTo(languages[position])

        holder.itemView.setSafeOnClickListener {
            listener(languages[position])
            notifyDataSetChanged()
        }
    }

    class LanguageViewHolder(val viewBinding: ItemLanguageBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bindTo(locale: Locale) {
            viewBinding.langName.text = locale.displayName.capitalize(AppConfig.APP_LOCALE)
            if (AppConfig.APP_LOCALE.toLanguageTag() == locale.toLanguageTag()) {
                viewBinding.languageLayout.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        com.majorik.base.R.color.colorAccent
                    )
                )
            } else {
                viewBinding.languageLayout.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        com.majorik.base.R.color.alabaster
                    )
                )
            }
        }
    }
}
