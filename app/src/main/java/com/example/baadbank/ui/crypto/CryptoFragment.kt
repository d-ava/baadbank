package com.example.baadbank.ui.crypto

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baadbank.databinding.FragmentCryptoBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.ui.recyclerAdapter.CryptoAdapter
import com.example.baadbank.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CryptoFragment : BaseFragment<FragmentCryptoBinding>(FragmentCryptoBinding::inflate) {

    private val viewModel: CryptoViewModel by activityViewModels()
    private lateinit var adapterCrypto: CryptoAdapter

    override fun start() {


        setRecycler()
//        adapterCrypto.setData(cryptoBody)

        getCoins()


    }

    private fun setRecycler() {
        adapterCrypto = CryptoAdapter()
        binding.recycler.apply {
            adapter = adapterCrypto
            layoutManager = LinearLayoutManager(requireContext())

        }
    }





    private fun getCoins() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadCoins03.collect {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()

                        }
                        is Resource.Success -> {
                            hideLoading()
                            adapterCrypto.setData(it.data!!)

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

}


