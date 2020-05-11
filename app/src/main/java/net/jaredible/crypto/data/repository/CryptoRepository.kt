package net.jaredible.crypto.data.repository

import androidx.lifecycle.MutableLiveData
import net.jaredible.crypto.App
import net.jaredible.crypto.Const
import net.jaredible.crypto.data.model.Crypto
import net.jaredible.crypto.data.model.response.CryptoResponse
import net.jaredible.crypto.data.source.local.db.AppDatabase
import net.jaredible.crypto.data.source.remote.factory.ServiceFactory
import net.jaredible.crypto.data.source.remote.CryptoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CryptoRepository {

    private val cryptoDb by lazy {
        AppDatabase.getDatabase(App.context).cryptoDao()
    }
    private val cryptoApi by lazy {
        ServiceFactory.newInstance<CryptoService>(Const.CRYPTO_API_BASE_URL)
    }

    suspend fun addCrypto(crypto: Crypto) = cryptoDb.insert(crypto)

    fun getAll(): MutableLiveData<CryptoResponse> {
        val cryptoData = MutableLiveData<CryptoResponse>()

        cryptoApi.getCryptos().enqueue(object : Callback<CryptoResponse> {
            override fun onResponse(call: Call<CryptoResponse>, response: Response<CryptoResponse>) {
                if (response.isSuccessful) {
                    cryptoData.value = response.body()
                }
            }

            override fun onFailure(call: Call<CryptoResponse>, t: Throwable) {
                cryptoData.value = null
            }
        })

        return cryptoData
    }

}