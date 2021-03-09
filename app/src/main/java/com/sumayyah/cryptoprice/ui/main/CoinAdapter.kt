package com.sumayyah.cryptoprice.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumayyah.cryptoprice.R
import com.sumayyah.cryptoprice.model.Market
import kotlinx.android.synthetic.main.coin_list_item.view.*
import timber.log.Timber

class CoinAdapter(val context: Context, val itemsList: ArrayList<Market>): RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(LayoutInflater.from(context).inflate(R.layout.coin_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = itemsList[position]
        holder.bind(coin.name, coin.price.toString())
    }

    class CoinViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(name: String, price: String) {
            view.name.text = name
            view.price.text = price
        }
    }

    fun swapData(newList: List<Market>) {
        if (newList.isNotEmpty()) {
            newList.forEach {
                if (!itemsList.contains(it)) {
                    itemsList.add(it)
                }
            }
            notifyDataSetChanged()
        }
    }

    fun clearData() {
        itemsList.clear()
    }
}

