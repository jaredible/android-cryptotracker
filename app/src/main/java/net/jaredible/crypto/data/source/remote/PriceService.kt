package net.jaredible.crypto.data.source.remote

import net.jaredible.crypto.data.model.response.PriceResponse
import retrofit2.Call
import retrofit2.http.GET

interface PriceService {

    @GET("prices/usd")
    fun getPrices(): Call<PriceResponse>

}