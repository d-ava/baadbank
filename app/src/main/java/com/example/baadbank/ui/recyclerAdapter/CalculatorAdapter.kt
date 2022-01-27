package com.example.baadbank.ui.recyclerAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baadbank.model.Converted
import com.example.baadbank.databinding.ItemCalculatorBinding

class CalculatorAdapter : RecyclerView.Adapter<CalculatorAdapter.CalculatorViewHolder>() {

    private val list: MutableList<Converted> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Converted>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalculatorViewHolder {
        return CalculatorViewHolder(
            ItemCalculatorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CalculatorViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CalculatorViewHolder(private val binding: ItemCalculatorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: Converted

        fun onBind() {
            model = list[adapterPosition]
            binding.apply {
                tvAmount.text = model.amount
                tvConvertedValue.text = model.result
                tvFromCurrency.text = model.fromCurrency
                tvToCurrency.text = model.toCurrency
            }


        }

    }


}