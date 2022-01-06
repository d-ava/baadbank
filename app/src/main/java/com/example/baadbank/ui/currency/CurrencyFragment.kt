package com.example.baadbank.ui.currency

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.baadbank.R
import com.example.baadbank.data.CurrencyItem
import com.example.baadbank.databinding.FragmentCurrencyBinding
import com.example.baadbank.network.NetworkClient
import com.example.baadbank.ui.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext


class CurrencyFragment : BaseFragment<FragmentCurrencyBinding>(FragmentCurrencyBinding::inflate) {

    override fun start() {

        lifecycleScope.launchWhenStarted {
            withContext(IO) {
                val response = NetworkClient.api.getCurrency()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.d("---", "$body")
                    lifecycleScope.launchWhenCreated {
                        withContext(Dispatchers.Main) {

                            for (item in body){
                                if (item.currency == "USD"){
                                   binding.tvCurrency.text = item.currency
                                   binding.tvValue.text = item.value.toString()
                                }
                            }
//                            binding.tvCurrency.text = body[0].currency
//                            binding.tvValue.text = body[0].value.toString()
                            binding.tvAll.text = body.toString()
                        }
                    }
                } else {
                    Log.d("---", "${response.code()}")

                }
            }
        }


    }
}