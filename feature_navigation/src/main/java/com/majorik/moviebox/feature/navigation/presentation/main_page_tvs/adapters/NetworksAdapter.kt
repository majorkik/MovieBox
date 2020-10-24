package com.majorik.moviebox.feature.navigation.presentation.main_page_tvs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.majorik.moviebox.feature.navigation.databinding.ItemNetworkBinding
import com.majorik.moviebox.feature.navigation.domain.models.NetworkModel
import com.majorik.moviebox.feature.navigation.presentation.main_page_tvs.adapters.NetworksAdapter.NetworkViewHolder
import org.jetbrains.annotations.NotNull

class NetworksAdapter() : RecyclerView.Adapter<NetworkViewHolder>() {

    var items: List<NetworkModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetworkViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemNetworkBinding.inflate(layoutInflater, parent, false)
        return NetworkViewHolder(binding)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: NetworkViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun addItems(networks: List<NetworkModel>) {
        items = networks
        notifyItemRangeInserted(0, networks.count())
    }

    class NetworkViewHolder(val view: @NotNull ItemNetworkBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(network: NetworkModel) {
            view.networkIcon.load(network.iconResId)
            view.networkName.text = network.name
        }
    }
}
