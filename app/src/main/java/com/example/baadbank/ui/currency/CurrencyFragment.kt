package com.example.baadbank.ui.currency

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baadbank.CurrencyItemsAdapter
import com.example.baadbank.databinding.FragmentCurrencyBinding
import com.example.baadbank.network.NetworkClient
import com.example.baadbank.ui.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

var currencyList: MutableList<String> = mutableListOf()

class CurrencyFragment : BaseFragment<FragmentCurrencyBinding>(FragmentCurrencyBinding::inflate) {

    private lateinit var adapter: CurrencyItemsAdapter

    override fun start() {

        setRecycler()

        lifecycleScope.launchWhenStarted {
            withContext(IO) {
                val response = NetworkClient.api.getCurrency()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.d("---", "$body")
                    lifecycleScope.launchWhenCreated {
                        withContext(Dispatchers.Main) {

                            for (item in body) {
                                if (item.currency == "USD") {
                                    binding.tvUSDCurrency.text = item.currency
                                    binding.tvUSDValue.text = item.value.toString()
                                }
                                if (item.currency == "EUR") {
                                    binding.tvEURCurrency.text = item.currency
                                    binding.tvEURValue.text = item.value.toString()
                                }
                                if (item.currency == "GBP") {
                                    binding.tvGBPCurrency.text = item.currency
                                    binding.tvGBPValue.text = item.value.toString()
                                }

                            }
                            adapter.setData(body)
                            for (c in body) {
                                currencyList.add(c.currency)
                            }
                        }
                    }
                } else {
                    Log.d("---", "${response.code()}")

                }
            }
        }


    }

    private fun setRecycler() {

        adapter = CurrencyItemsAdapter()
        binding.apply {
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(requireContext())
        }


    }


}