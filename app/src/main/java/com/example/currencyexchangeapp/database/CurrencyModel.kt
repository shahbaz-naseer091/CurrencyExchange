package com.example.currencyexchangeapp.database


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "currency_table")
data class CurrencyModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "code")
    var code: String? = null,
    @ColumnInfo(name = "value")
    var value: Double = 0.0,
) : Serializable