package com.example.baadbank.ui.calculator

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baadbank.R
import com.example.baadbank.data.Converted
import com.example.baadbank.databinding.FragmentCalculatorBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.ui.CalculatorAdapter
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.convertedList
import com.example.baadbank.util.Utils.currencyList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode


var fromCurrency = ""
var toCurrency = ""
var amount = ""
var result = ""

@AndroidEntryPoint
class CalculatorFragment :
    BaseFragment<FragmentCalculatorBinding>(FragmentCalculatorBinding::inflate) {

    private val viewModel: CalculatorViewModel by activityViewModels()

    private lateinit var calculatorAdapter: CalculatorAdapter

    override fun start() {

        setSpinners()
        setListeners()
        setRecycler()


    }


    private fun setListeners() {
        binding.btnConvert.setOnClickListener {


            amount = if (binding.etAmount.text.isNullOrEmpty()) {
                ""
            } else {
                binding.etAmount.text.toString().toDouble().toInt().toString()
            }



            binding.etAmount.text?.clear()
            currencyConverter()


        }
    }


    private fun currencyConverter() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.result.collect {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
                        }
                        is Resource.Success -> {

                            hideLoading()


                            val value = BigDecimal(it.data!!).setScale(2, RoundingMode.HALF_EVEN)
                                .toPlainString().toString()
                            binding.tvValue.text = value

                            result = value



                            convertedList.add(Converted(fromCurrency, toCurrency, amount, result))

                            calculatorAdapter.setData(convertedList)


                        }
                        is Resource.Error -> {
                            hideLoading()
                            view?.makeSnackbar("${it.message}")
                        }
                    }
                }
            }
        }
    }


    private fun setRecycler() {
        calculatorAdapter = CalculatorAdapter()
        binding.recycler.apply {
            adapter = calculatorAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }


    }


    private fun setSpinners() {

        val arrAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.bb_spinner_item,
                currencyList

            )

        val spinnerFrom = binding.spinnerLeft


        spinnerFrom.adapter = arrAdapter

        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                fromCurrency = adapterView?.getItemAtPosition(position).toString()


            }


            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        val spinnerTo = binding.spinnerRight
        spinnerTo.adapter = arrAdapter
        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                toCurrency = adapterView?.getItemAtPosition(position).toString()


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


    }


}