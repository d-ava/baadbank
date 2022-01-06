package com.example.baadbank.ui.calculator

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.baadbank.R
import com.example.baadbank.databinding.FragmentCalculatorBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.network.NetworkClient
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.ui.currency.currencyList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext


var fromCurrency = ""
var toCurrency = ""
var amount = ""

class CalculatorFragment :
    BaseFragment<FragmentCalculatorBinding>(FragmentCalculatorBinding::inflate) {

    override fun start() {

        setSpinners()
        setListeners()



    }


    private fun setListeners() {
        binding.btnConvert.setOnClickListener {
            amount = binding.etAmount.text.toString()
binding.etAmount.text?.clear()
            currencyConverter()
        }
    }

    private fun currencyConverter() {

        lifecycleScope.launchWhenStarted {
            withContext(IO) {
                val response = NetworkClient.apiConvert.convertCurrency()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.d("---", body.value.toString())
                    lifecycleScope.launchWhenCreated {
                        withContext(Dispatchers.Main) {
                            binding.tvValue.text = body.value.toString()
                        }
                    }

                } else {
                    Log.d("---", response.message())
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
//                view?.makeSnackbar(adapterView?.getItemAtPosition(position).toString())

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


}