package net.jaredible.crypto.data.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.jaredible.crypto.App
import net.jaredible.crypto.util.Const
import net.jaredible.crypto.data.model.Crypto
import net.jaredible.crypto.data.model.response.CryptoResponse
import net.jaredible.crypto.data.source.local.db.AppDatabase
import net.jaredible.crypto.data.source.remote.factory.ServiceFactory
import net.jaredible.crypto.data.source.remote.CryptoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val TAG = CryptoRepository::class.simpleName

object CryptoRepository {

    private val cryptoDao by lazy { AppDatabase.getDatabase(App.context).cryptoDao() }
    private val cryptoService by lazy { ServiceFactory.newInstance<CryptoService>(Const.CRYPTO_API_BASE_URL) }

    fun updateCryptos(): LiveData<Unit> {
        val result = MutableLiveData<Unit>()

        cryptoService.getCryptos().enqueue(object : Callback<CryptoResponse> {
            override fun onFailure(call: Call<CryptoResponse>, t: Throwable) {
                Log.e(TAG, "Something went wrong when updating cryptos!")
                result.postValue(Unit)
            }

            override fun onResponse(call: Call<CryptoResponse>, response: Response<CryptoResponse>) {
                response.body()?.let {
                    Thread(Runnable {
                        cryptoDao.insert(it.data.values.toList())
                        result.postValue(Unit)
                        App.context.notifyCryptosUpdated()
                    }).start()
                }
            }
        })

        return result
    }

    fun getAllCryptos() = cryptoDao.get()

    fun getCrypto(symbol: String) = cryptoDao.get(symbol)

    fun addCrypto(crypto: Crypto) {
        AddCryptoTask().execute(crypto)
    }

    fun updateCrypto(crypto: Crypto) {
        UpdateCryptoTask().execute(crypto)
    }

    fun deleteCrypto(crypto: Crypto) {
        DeleteCryptoTask().execute(crypto)
    }

    fun deleteAllCryptos() {
        DeleteAllCryptosTask().execute()
    }

    private class AddCryptoTask : AsyncTask<Crypto?, Void?, Void?>() {
        override fun doInBackground(vararg params: Crypto?): Void? {
            params[0]?.let { cryptoDao.insert(it) }
            return null
        }
    }

    private class UpdateCryptoTask : AsyncTask<Crypto?, Void?, Void?>() {
        override fun doInBackground(vararg params: Crypto?): Void? {
            params[0]?.let { cryptoDao.update(it) }
            return null
        }
    }

    private class DeleteCryptoTask : AsyncTask<Crypto?, Void?, Void?>() {
        override fun doInBackground(vararg params: Crypto?): Void? {
            params[0]?.let { cryptoDao.insert(it) }
            return null
        }
    }

    private class DeleteAllCryptosTask : AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            cryptoDao.delete()
            return null
        }
    }

}