package net.jaredible.crypto.data.repository

import android.os.AsyncTask
import net.jaredible.crypto.App
import net.jaredible.crypto.Const
import net.jaredible.crypto.data.model.Price
import net.jaredible.crypto.data.source.local.db.AppDatabase
import net.jaredible.crypto.data.source.remote.PriceService
import net.jaredible.crypto.data.source.remote.factory.ServiceFactory

object PriceRepository {

    private val priceDao by lazy { AppDatabase.getDatabase(App.context).priceDao() }
    private val cryptoService by lazy { ServiceFactory.newInstance<PriceService>(Const.CRYPTO_API_BASE_URL) }

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