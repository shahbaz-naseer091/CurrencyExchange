package com.example.currencyexchangeapp.utlis

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun isFirstTime(): String {
        return sharedPreferences.getString("FirstTime", "Yes")!!
    }

    fun setFirstTime(query: String) {
        sharedPreferences.edit().putString("FirstTime", query).apply()
    }

    fun lastAPICallTime(): String {
        return sharedPreferences.getString("APITime", "")!!
    }

    fun setLastAPICallTime(query: String) {
        sharedPreferences.edit().putString("APITime", query).apply()
    }
}