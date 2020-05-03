package com.majorik.moviebox.feature.collections.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.collections.domain.tmdbModels.production.ProductionCompany
import com.majorik.moviebox.feature.collections.presentation.adapters.CompaniesAdapter.*
import kotlinx.android.synthetic.main.item_company.view.*
import com.majorik.moviebox.feature.collections.R

class CompaniesAdapter(private val companies: List<ProductionCompany>) :
    RecyclerView.Adapter<CompanyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_company, parent, false)

        return CompanyViewHolder(view)
    }

    override fun getItemCount() = companies.size

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bindTo(companies[position])
    }

    class CompanyViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(company: ProductionCompany) {
            itemView.company_name.text = company.name
        }
    }
}
