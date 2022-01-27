package com.example.baadbank.ui.recyclerAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baadbank.model.CurrencyItem
import com.example.baadbank.databinding.ItemCurrencyBinding
import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyItemsAdapter : RecyclerView.Adapter<CurrencyItemsAdapter.CurrencyItemViewHolder>() {

    private val list: MutableList<CurrencyItem> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<CurrencyItem>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyItemViewHolder {
        return CurrencyItemViewHolder(
            ItemCurrencyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: CurrencyItemViewHolder,
        position: Int
    ) {
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CurrencyItemViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: CurrencyItem

        fun onBind() {
            model = list[adapterPosition]
            binding.apply {
                tvCurrency.text = model.currency
//                val value = model.value
                tvValue.text =
                    BigDecimal(model.value).setScale(2, RoundingMode.HALF_EVEN).toPlainString()
                        .toString()

//                tvValue.text = model.value.toString()
            }
        }


    }


}