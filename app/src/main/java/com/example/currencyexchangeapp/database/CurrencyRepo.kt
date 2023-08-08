package com.example.currencyexchangeapp.database

import androidx.room.Room


class CurrencyRepo(context: android.content.Context) {
    private val database: CurrencyDatabase = Room.databaseBuilder(
        context.applicationContext, CurrencyDatabase::class.java, "currency-database"
    ).allowMainThreadQueries().build()

    fun insert(drink: CurrencyModel) = database.currencyDao().insertCurrency(drink)
    fun getAllCurrencies() = database.currencyDao().getAllCurrencies()
    fun deleteAllCurrencies() = database.currencyDao().deleteAllCurrencies()
}
