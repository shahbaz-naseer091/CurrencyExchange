package com.example.currencyexchangeapp.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.currencyexchangeapp.databinding.MainActivityLayoutBinding
import com.example.currencyexchangeapp.ui.viewModel.CurrencyViewModel

class MainActivity : ComponentActivity() {

    private lateinit var binding: MainActivityLayoutBinding
    private lateinit var viewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = CurrencyViewModel(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        binding.viewModel = viewModel

        initializeUI()

    }

    private fun initializeUI() {
        binding.apply {
            currenciesRecyclerView.layoutManager = GridLayoutManager(this@MainActivity, 4)
            viewModel!!.currencies.observe(this@MainActivity) {


                val adapter =
                    ArrayAdapter(
                        this@MainActivity,
                        android.R.layout.simple_spinner_item,
                        it.keys.toList()
                    )
                spinnerCurrency.adapter = adapter

                spinnerCurrency.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        viewModel!!.selectCurrency(it.toList()[position])
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        viewModel!!.selectCurrency(it.toList()[0])
                    }
                }
            }
        }
    }

}
