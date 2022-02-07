package com.example.baadbank.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baadbank.data.Converted
import com.example.baadbank.databinding.ItemCalculatorBinding
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import java.text.SimpleDateFormat
import java.util.*

class CalculatorAdapter : RecyclerView.Adapter<CalculatorAdapter.CalculatorViewHolder>() {


    private val list: MutableList<Converted> = mutableListOf()


    fun setData(list: List<Converted>) {
        this.list.clear()
        this.list.addAll(list)
        notifyItemChanged(-1)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalculatorAdapter.CalculatorViewHolder {
        return CalculatorViewHolder(
            ItemCalculatorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CalculatorAdapter.CalculatorViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CalculatorViewHolder(private val binding: ItemCalculatorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: Converted

        @SuppressLint("SimpleDateFormat")
        fun onBind() {


            model = list[absoluteAdapterPosition]
            binding.apply {
                tvAmount.text = model.amount
                tvConvertedValue.text = model.result
                tvFromCurrency.text = model.fromCurrency
                tvToCurrency.text = model.toCurrency

            }

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            binding.tvTime.text = currentDate

        }

    }


}