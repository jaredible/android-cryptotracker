package net.jaredible.crypto

import android.app.Application
import android.content.Context
import net.jaredible.crypto.data.model.Crypto
import net.jaredible.crypto.data.model.Price
import net.jaredible.crypto.data.model.Wallet
import net.jaredible.crypto.data.repository.CryptoRepository
import net.jaredible.crypto.data.repository.PriceRepository
import net.jaredible.crypto.data.repository.WalletRepository
import java.math.BigDecimal

class App : Application() {

    companion object {
        lateinit var context: Context private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        initDatabase()
    }

    private fun initDatabase() {
        CryptoRepository.deleteAllCryptos()
        CryptoRepository.addCrypto(Crypto("BTC", "Bitcoin"))
        CryptoRepository.addCrypto(Crypto("ETH", "Ethereum"))
        CryptoRepository.addCrypto(Crypto("LTC", "Litecoin"))
        CryptoRepository.addCrypto(Crypto("XRP", "Ripple"))
        CryptoRepository.addCrypto(Crypto("BAT", "Basic Attention Token"))
        CryptoRepository.addCrypto(Crypto("DOGE", "Dogecoin"))

        PriceRepository.deleteAllPrices()
        PriceRepository.addPrice(Price("BTC", 9496.98))
        PriceRepository.addPrice(Price("ETH", 201.29))
        PriceRepository.addPrice(Price("LTC", 43.58))
        PriceRepository.addPrice(Price("XRP", 0.2010))
        PriceRepository.addPrice(Price("BAT", 0.2038))
        PriceRepository.addPrice(Price("DOGE", 0.002564))

        WalletRepository.deleteAllWallets()
        WalletRepository.addWallet(Wallet("My Bitcoin", "BTC", 1.0))
        WalletRepository.addWallet(Wallet("My Ethereum", "ETH", 1.2))
        WalletRepository.addWallet(Wallet("My Litecoin", "LTC", 1.4))
        WalletRepository.addWallet(Wallet("My Ripple", "XRP", 1.6))
        WalletRepository.addWallet(Wallet("My Basic Attention Token", "BAT", 1.8))
        WalletRepository.addWallet(Wallet("My Dogecoin", "DOGE", 2.0))
    }

}