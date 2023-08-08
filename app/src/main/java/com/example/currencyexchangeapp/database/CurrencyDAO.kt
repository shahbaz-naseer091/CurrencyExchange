package com.example.currencyexchangeapp.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface CurrencyDAO {

    @Insert(onConflict = REPLACE)
    fun insertCurrency(drink: CurrencyModel)

    @Query(value = "SELECT * FROM currency_table")
    fun getAllCurrencies(): Flow<List<CurrencyModel>>

    @Query(value = "DELETE FROM currency_table")
    fun deleteAllCurrencies()

}