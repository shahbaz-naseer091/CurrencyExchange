package com.example.currencyexchangeapp.utlis

import com.example.currencyexchangeapp.model.CurrencyResponse
import com.example.currencyexchangeapp.utlis.Constant.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class APIRepository {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val currencyService: CurrencyService = retrofit.create(CurrencyService::class.java)


    suspend fun getLatestCurrencies(): CurrencyResponse {
        return currencyService.getLatestCurrencies("d3a85779b4ea4e8da79432fb2d028241")

    }

}


interface CurrencyService {
    @GET("latest.json")
    suspend fun getLatestCurrencies(@Query("app_id") id: String): CurrencyResponse

}
