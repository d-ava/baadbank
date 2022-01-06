package com.example.baadbank.ui.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.baadbank.R
import com.example.baadbank.databinding.FragmentCalculatorBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.ui.BaseFragment


class CalculatorFragment : BaseFragment<FragmentCalculatorBinding>(FragmentCalculatorBinding::inflate) {

    override fun start() {

        setSpinners()

    }



    private fun setSpinners() {

        val list = listOf<String>("EUR", "USD", "RTE", "GEL", "ERT", "RUB")
        val arrAdapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, list)

        val spinnerLeft = binding.spinnerLeft
        spinnerLeft.adapter = arrAdapter
        spinnerLeft.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                view?.makeSnackbar(adapterView?.getItemAtPosition(position).toString())

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        val spinnerRight = binding.spinnerRight
        spinnerRight.adapter = arrAdapter
        spinnerRight.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                view?.makeSnackbar(adapterView?.getItemAtPosition(position).toString())

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }








    }



}