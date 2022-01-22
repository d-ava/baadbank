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
import com.example.baadbank.data.Converted
import com.example.baadbank.databinding.FragmentCalculatorBinding
import com.example.baadbank.extensions.makeSnackbar

import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.convertedList
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
var result = ""

@AndroidEntryPoint
class CalculatorFragment :
    BaseFragment<FragmentCalculatorBinding>(FragmentCalculatorBinding::inflate) {

    private val viewModel: CalculatorViewModel by activityViewModels()

    override fun start() {

        setSpinners()
        setListeners()


    }


    private fun setListeners() {
        binding.btnConvert.setOnClickListener {
            amount = binding.etAmount.text.toString()
//            Log.d("---", "from - $fromCurrency, to - $toCurrency, value - $amount")
            binding.etAmount.text?.clear()
            currencyConverter03()
//            currencyConverter00000001()
        }
    }

    private fun currencyConverter00000001() {
        viewModel.calculateValue011111111111()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadCalculatedValue0001111.collect {
                    Log.d("---", "fragment value $it")
                    binding.tvValue.text=it.toString()
                }
            }
        }

    }

    private fun currencyConverter03(){
        viewModel.calculateValue03()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.loadCalculatedValue03.collect {
                    when(it){
                        is Resource.Loading -> {
                            showLoading()
                        }
                        is Resource.Success -> {
                            hideLoading()
                            binding.tvValue.text=it.data.toString()
                        }
                        is Resource.Error -> {
                            hideLoading()
                            view?.makeSnackbar("${it.message}")
                        }

                    }                    }
                }
            }
    }

//    private fun currencyConverter01() {
//        viewModel.loadCalculatedValue()
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//
//                viewModel.loadCalculatedValue.collect {
//                    Log.d("---", "result is $it")
//                    convertedList.add(
//                        Converted(
//                            fromCurrency,
//                            toCurrency,
//                            amount = amount,
//                            it.toString()
//                        )
//                    )
//                    Log.d("---", "list =  $convertedList")
//                }
//
//
//            }
//        }
//    }


//    private fun currencyConverter00() {
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.loadConverter.collect {
//                    when (it) {
//                        is Resource.Loading -> {
//                            progressBar(true)
//                        }
//                        is Resource.Success -> {
//                            progressBar(false)
//                            binding.tvValue.text = it.data!!.value.toString()
//
//                            convertedList.add(
//                                Converted(
//                                    fromCurrency,
//                                    toCurrency,
//                                    amount = amount,
//                                    it.data.value.toString()
//                                )
//                            )
//                            Log.d("---", "list =  $convertedList")
//                        }
//                        is Resource.Error -> {
//                            progressBar(false)
//                            view?.makeSnackbar("${it.message}")
//                        }
//
//                    }
//                }
//
//
//            }
//        }
//
//
//    }


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