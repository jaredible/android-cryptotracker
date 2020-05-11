package net.jaredible.crypto.data.repository

import net.jaredible.crypto.App
import net.jaredible.crypto.data.model.Wallet
import net.jaredible.crypto.data.source.local.db.AppDatabase

object WalletRepository {

    private val walletDao by lazy {
        AppDatabase.getDatabase(App.context).walletDao()
    }

    fun getWallets() = walletDao.getWallets()

    fun getWallet(name: String) = walletDao.getWallet(name)

    suspend fun addWallet(wallet: Wallet) = walletDao.insert(wallet)

}