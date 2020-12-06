package com.majorik.moviebox.feature.details.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.majorik.moviebox.feature.details.databinding.ItemCompanyDetailsBinding
import com.majorik.moviebox.feature.details.domain.tmdbModels.production.ProductionCompany
import com.majorik.moviebox.feature.details.presentation.adapters.CompaniesAdapter.*

class CompaniesAdapter(private val companies: List<ProductionCompany>) :
    RecyclerView.Adapter<CompanyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemCompanyDetailsBinding.inflate(layoutInflater, parent, false)

        return CompanyViewHolder(viewBinding)
    }

    override fun getItemCount() = companies.size

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bindTo(companies[position])
    }

    class CompanyViewHolder(val viewBinding: ItemCompanyDetailsBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bindTo(company: ProductionCompany) {
            viewBinding.companyName.text = company.name
        }
    }
}
