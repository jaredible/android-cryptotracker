package net.jaredible.crypto.data.source.remote

import net.jaredible.crypto.data.model.response.CryptoResponse
import retrofit2.Call
import retrofit2.http.GET

interface CryptoService {

    @GET("coins")
    fun getCryptos(): Call<CryptoResponse>

}