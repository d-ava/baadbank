package com.example.baadbank.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baadbank.data.CoinGecko
import com.example.baadbank.databinding.ItemCryptoBinding
import com.example.baadbank.extensions.glideExtension

class CryptoAdapter : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    private val list: MutableList<CoinGecko> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<CoinGecko>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CryptoViewHolder {
        return CryptoViewHolder(
            ItemCryptoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CryptoViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: CoinGecko

        @SuppressLint("SetTextI18n")
        fun onBind() {
            model = list[adapterPosition]
            binding.ivCoin.glideExtension(model.image)
            binding.apply {
                tvCurrentPrice.text ="$ " + model.currentPrice.toString()
                tvName.text = model.name
                tvSymbol.text = model.symbol
                tvMarketCap.text = "$ " + model.marketCap.toString()
            }

        }

    }


}