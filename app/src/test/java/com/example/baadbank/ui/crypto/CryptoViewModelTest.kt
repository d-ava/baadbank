package com.example.baadbank.ui.crypto

import com.example.baadbank.repository.FakeCryptoRepositoryImpl
import org.junit.Assert.*
import org.junit.Before

class CryptoViewModelTest{

    private lateinit var viewModel: CryptoViewModel

    @Before
    fun setup(){
        viewModel = CryptoViewModel(FakeCryptoRepositoryImpl())
    }

}