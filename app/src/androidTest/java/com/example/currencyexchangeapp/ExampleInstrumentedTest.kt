package com.example.currencyexchangeapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.currencyexchangeapp.ui.viewModel.CurrencyViewModel
import com.example.currencyexchangeapp.utlis.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val appContext = ApplicationProvider.getApplicationContext<Context>()
    private val viewModel = CurrencyViewModel(appContext) {}

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun fetchAPI_Test() {
        viewModel.fetchCurrencies()
        val response = viewModel.currencies.getOrAwaitValue()

        assertNotNull(response)
    }

    @Test
    fun convertCurrency_Test() {
        viewModel.desiredAmount.set("100")
        val result = viewModel.updateConversion()
        assertTrue(result)
    }
}