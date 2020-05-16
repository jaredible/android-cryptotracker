package net.jaredible.crypto.data.repository

import android.os.AsyncTask
import net.jaredible.crypto.App
import net.jaredible.crypto.Const
import net.jaredible.crypto.data.model.Crypto
import net.jaredible.crypto.data.source.local.db.AppDatabase
import net.jaredible.crypto.data.source.remote.factory.ServiceFactory
import net.jaredible.crypto.data.source.remote.CryptoService

object CryptoRepository {

    private val cryptoDao by lazy { AppDatabase.getDatabase(App.context).cryptoDao() }
    private val cryptoService by lazy { ServiceFactory.newInstance<CryptoService>(Const.CRYPTO_API_BASE_URL) }

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