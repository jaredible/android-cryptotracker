package net.jaredible.crypto.data.repository

import android.os.AsyncTask
import net.jaredible.crypto.App
import net.jaredible.crypto.data.model.Wallet
import net.jaredible.crypto.data.source.local.db.AppDatabase

object WalletRepository {

    private val walletDao by lazy { AppDatabase.getDatabase(App.context).walletDao() }

    fun getAllWallets() = walletDao.get()

    fun getWallet(id: Int) = walletDao.get(id)

    fun addWallet(wallet: Wallet) {
        AddWalletTask().execute(wallet)
    }

    fun updateWallet(wallet: Wallet) {
        UpdateWalletTask().execute(wallet)
    }

    fun deleteWallet(wallet: Wallet) {
        DeleteWalletTask().execute(wallet)
    }

    fun deleteAllWallets() {
        DeleteAllWalletsTask().execute()
    }

    private class AddWalletTask : AsyncTask<Wallet?, Void?, Void?>() {
        override fun doInBackground(vararg params: Wallet?): Void? {
            params[0]?.let { walletDao.insert(it) }
            return null
        }
    }

    private class UpdateWalletTask : AsyncTask<Wallet?, Void?, Void?>() {
        override fun doInBackground(vararg params: Wallet?): Void? {
            params[0]?.let { walletDao.update(it) }
            return null
        }
    }

    private class DeleteWalletTask : AsyncTask<Wallet?, Void?, Void?>() {
        override fun doInBackground(vararg params: Wallet?): Void? {
            params[0]?.let { walletDao.delete(it) }
            return null
        }
    }

    private class DeleteAllWalletsTask : AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            return null
        }
    }

}