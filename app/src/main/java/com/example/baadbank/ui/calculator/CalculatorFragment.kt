package com.example.baadbank.ui.calculator

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baadbank.R
import com.example.baadbank.databinding.FragmentCalculatorBinding
import com.example.baadbank.extensions.makeSnackbar

import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.currencyList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


var fromCurrency = ""
var toCurrency = ""
var amount = ""

@AndroidEntryPoint
class CalculatorFragment :
    BaseFragment<FragmentCalculatorBinding>(FragmentCalculatorBinding::inflate) {

    private val viewModel:CalculatorViewModel by activityViewModels()

    override fun start() {

        setSpinners()
        setListeners()


    }


    private fun setListeners() {
        binding.btnConvert.setOnClickListener {
            amount = binding.etAmount.text.toString()
            binding.etAmount.text?.clear()
            currencyConverter00()
        }
    }

    private fun currencyConverter00() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadConverter.collect {
                    when(it){
                        is Resource.Loading -> {
                            progressBar(true)
                        }
                        is Resource.Success -> {
                            progressBar(false)
                            binding.tvValue.text = it.data!!.value.toString()
                        }
                        is Resource.Error -> {
                            progressBar(false)
                            view?.makeSnackbar("${it.message}")
                        }
                    }
                }



            }
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
//                view?.makeSnackbar(adapterView?.getItemAtPosition(position).toString())

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


    }


    private fun progressBar(visible: Boolean) {
        binding.progressbar.isVisible = visible
    }


}