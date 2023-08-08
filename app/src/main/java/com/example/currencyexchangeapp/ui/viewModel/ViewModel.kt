package com.example.currencyexchangeapp.ui.viewModel

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchangeapp.database.CurrencyModel
import com.example.currencyexchangeapp.database.CurrencyRepo
import com.example.currencyexchangeapp.ui.adapter.GridAdapter
import com.example.currencyexchangeapp.utlis.APIRepository
import com.example.currencyexchangeapp.utlis.Constant.Companion.EMPTY
import com.example.currencyexchangeapp.utlis.Constant.Companion.NO
import com.example.currencyexchangeapp.utlis.Constant.Companion.NO_INTERNET
import com.example.currencyexchangeapp.utlis.Constant.Companion.PLEASE_ENTER_AMOUNT
import com.example.currencyexchangeapp.utlis.Constant.Companion.SHOWING_LAST_SAVE_DATA
import com.example.currencyexchangeapp.utlis.Constant.Companion.YES
import com.example.currencyexchangeapp.utlis.NetworkUtils.isInternetAvailable
import com.example.currencyexchangeapp.utlis.SharedPrefs
import com.example.currencyexchangeapp.utlis.getCurrentDateTime
import kotlinx.coroutines.launch


class CurrencyViewModel(context: Context, val callback: (String) -> Unit) : ViewModel() {

    private val repository = APIRepository()
    private val currencyRepo = CurrencyRepo(context)
    private val sharedPrefs = SharedPrefs(context)
    private val internetAvailable = isInternetAvailable(context)
    private val refreshInterval = 30 * 60 * 1000

    private val _selectedCurrency: MutableLiveData<Pair<String, Double>> = MutableLiveData()

    var desiredAmount: ObservableField<String> = ObservableField(EMPTY)

    var isListVisible: ObservableField<Boolean> = ObservableField(false)

    private val _currencies = MutableLiveData<Map<String, Double>>()
    val currencies: LiveData<Map<String, Double>> = _currencies

    val gridAdapter = GridAdapter()

    init {
        fetchCurrencies()
    }


    fun selectCurrency(currency: Pair<String, Double>) {
        _selectedCurrency.value = currency
    }

    fun updateConversion() : Boolean {
        val desiredAmount = desiredAmount.get()
        return if (!desiredAmount.isNullOrEmpty()) {
            val convertedCurrencies: ArrayList<Pair<String, Double>> = ArrayList()
            val selectedCurrencyInUSD = currencies.value?.get(_selectedCurrency.value?.first)
            _currencies.value?.forEach { currency ->
                val currencyToInUSD = currency.value
                val converted = currencyToInUSD.div(selectedCurrencyInUSD!!)
                    .times(desiredAmount.toDoubleOrNull()!!)
                convertedCurrencies.add(Pair(currency.key, converted))
            }
            setGridData(convertedCurrencies)
            true
        } else {
            callback(PLEASE_ENTER_AMOUNT)
            false
        }
    }

    fun fetchCurrencies() {
        if (internetAvailable) {
            val interval = sharedPrefs.lastAPICallTime()
            val currentTime = getCurrentDateTime()
            if (interval.isEmpty() || (currentTime - interval.toLong() > refreshInterval) || sharedPrefs.isFirstTime() == YES) {
                viewModelScope.launch {
                    val response = repository.getLatestCurrencies()
                    _currencies.value = response.rates
                    saveCurrenciesToDB()
                    sharedPrefs.setLastAPICallTime(getCurrentDateTime().toString())
                    sharedPrefs.setFirstTime(NO)
                }
            } else {
                fetchCurrenciesFromDB()
                callback(SHOWING_LAST_SAVE_DATA)
            }
        } else {
            fetchCurrenciesFromDB()
            callback(NO_INTERNET)
        }
    }

    private fun saveCurrenciesToDB() {

        viewModelScope.launch {
            currencyRepo.deleteAllCurrencies()
            _currencies.value?.forEach { currency ->
                currencyRepo.insert(CurrencyModel(code = currency.key, value = currency.value))
            }
        }
    }

    private fun fetchCurrenciesFromDB() {
        viewModelScope.launch {
            currencyRepo.getAllCurrencies().collect { model ->
                _currencies.value = model.associateBy(
                    { it.code!! },
                    { it.value }
                )
            }
        }
    }

    private fun setGridData(list: ArrayList<Pair<String, Double>>) {
        if (list.isNotEmpty()) {
            isListVisible.set(true)
        }
        gridAdapter.setData(list)
    }

    fun clear() {
        desiredAmount.set(EMPTY)
        isListVisible.set(false)
    }
}
