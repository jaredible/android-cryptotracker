package net.jaredible.crypto.data.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.jaredible.crypto.App
import net.jaredible.crypto.util.Const
import net.jaredible.crypto.data.model.Price
import net.jaredible.crypto.data.model.response.PriceResponse
import net.jaredible.crypto.data.source.local.db.AppDatabase
import net.jaredible.crypto.data.source.remote.PriceService
import net.jaredible.crypto.data.source.remote.factory.ServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val TAG = PriceRepository::class.simpleName

object PriceRepository {

    private val priceDao by lazy { AppDatabase.getDatabase(App.context).priceDao() }
    private val priceService by lazy { ServiceFactory.newInstance<PriceService>(Const.CRYPTO_API_BASE_URL) }

    fun updatePrices(): LiveData<Unit> {
        val result = MutableLiveData<Unit>()

        priceService.getPrices().enqueue(object : Callback<PriceResponse> {
            override fun onFailure(call: Call<PriceResponse>, t: Throwable) {
                Log.e(TAG, "Something went wrong when updating prices!")
                result.postValue(Unit)
            }

            override fun onResponse(call: Call<PriceResponse>, response: Response<PriceResponse>) {
                response.body()?.let {
                    Thread(Runnable {
                        priceDao.insert(it.data.values.toList())
                        result.postValue(Unit)
                        App.context.notifyPricesUpdated()
                    }).start()
                }
            }
        })

        return result
    }

    fun getAllPrices() = priceDao.get()

    fun getPrice(cryptoSymbol: String) = priceDao.get(cryptoSymbol)

    fun addPrice(price: Price) {
        AddPriceTask().execute(price)
    }

    fun updatePrice(price: Price) {
        UpdatePriceTask().execute(price)
    }

    fun deletePrice(price: Price) {
        DeletePriceTask().execute(price)
    }

    fun deleteAllPrices() {
        DeleteAllPricesTask().execute()
    }

    private class AddPriceTask : AsyncTask<Price?, Void?, Void?>() {
        override fun doInBackground(vararg params: Price?): Void? {
            params[0]?.let { priceDao.insert(it) }
            return null
        }
    }

    private class UpdatePriceTask : AsyncTask<Price?, Void?, Void?>() {
        override fun doInBackground(vararg params: Price?): Void? {
            params[0]?.let { priceDao.update(it) }
            return null
        }
    }

    private class DeletePriceTask : AsyncTask<Price?, Void?, Void?>() {
        override fun doInBackground(vararg params: Price?): Void? {
            params[0]?.let { priceDao.delete(it) }
            return null
        }
    }

    private class DeleteAllPricesTask : AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            priceDao.delete()
            return null
        }
    }

}