package com.example.currencyexchangeapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangeapp.R
import com.example.currencyexchangeapp.databinding.GridItemLayoutBinding
import com.example.currencyexchangeapp.utlis.formatNumber

class GridAdapter :
    RecyclerView.Adapter<GridAdapter.CurrencyViewHolder>() {

    private var currencyList: List<Pair<String, Double>> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item_layout, parent, false)
        return CurrencyViewHolder(view)
    }

    fun setData(data: List<Pair<String, Double>>) {
        currencyList = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bindLayout(currencyList[position])
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    inner class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = GridItemLayoutBinding.bind(view)

        fun bindLayout(item: Pair<String, Double>) {

            binding.currencyTextView.text = item.first
            if (item.second > 1) {
                binding.currencyAmountTextView.text = item.second.formatNumber(2)
            } else {
                binding.currencyAmountTextView.text = item.second.formatNumber(5)
            }

        }

    }
}
