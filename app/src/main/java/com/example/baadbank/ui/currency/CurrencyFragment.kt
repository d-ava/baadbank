package com.example.baadbank.ui.currency

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baadbank.CurrencyItemsAdapter
import com.example.baadbank.databinding.FragmentCurrencyBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.network.NetworkClient
import com.example.baadbank.ui.BaseFragment

import com.example.baadbank.ui.login.currencyBody
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.currencyList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



@AndroidEntryPoint
class CurrencyFragment : BaseFragment<FragmentCurrencyBinding>(FragmentCurrencyBinding::inflate) {

    private val viewModel: CurrencyViewModel by activityViewModels()
    private lateinit var adapter: CurrencyItemsAdapter

    override fun start() {

        setRecycler()
//        getCurrency()
        getCurrency00()


//        for (item in currencyBody) {
//            if (item.currency == "USD") {
//                binding.tvUSDCurrency.text = item.currency
//                binding.tvUSDValue.text = item.value.toString()
//            }
//            if (item.currency == "EUR") {
//                binding.tvEURCurrency.text = item.currency
//                binding.tvEURValue.text = item.value.toString()
//            }
//            if (item.currency == "GBP") {
//                binding.tvGBPCurrency.text = item.currency
//                binding.tvGBPValue.text = item.value.toString()
//            }
//
//        }
//        adapter.setData(currencyBody)
//        for (c in currencyBody) {
//            currencyList.add(c.currency)
//        }


    }

    private fun getCurrency00() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadCurrency.collect {
                    when (it){
                        is Resource.Loading -> {
                            progressBar(true)
                        }
                        is Resource.Success -> {
                           progressBar(false)
                            for (item in it.data!!){
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

                           adapter.setData(it.data)
                            for (c in it.data) {
                                currencyList.add(c.currency)
                            }


                        }
                        is Resource.Error -> {
                            view?.makeSnackbar("${it.message}")
                        }
                    }
                }


            }
        }
    }

    private fun progressBar(visible: Boolean) {
        binding.progressbar.isVisible = visible
    }

    private fun getCurrency() {
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