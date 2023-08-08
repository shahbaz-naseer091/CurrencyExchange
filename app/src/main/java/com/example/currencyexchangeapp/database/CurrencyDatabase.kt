package com.example.currencyexchangeapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyModel::class], version = 1, exportSchema = true)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDAO
}