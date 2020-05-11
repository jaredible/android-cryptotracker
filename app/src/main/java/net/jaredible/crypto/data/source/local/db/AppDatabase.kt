package net.jaredible.crypto.data.source.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.jaredible.crypto.data.model.Crypto
import net.jaredible.crypto.data.model.Price
import net.jaredible.crypto.data.model.Wallet
import net.jaredible.crypto.data.source.local.db.dao.CryptoDao
import net.jaredible.crypto.data.source.local.db.dao.PriceDao
import net.jaredible.crypto.data.source.local.db.dao.WalletDao

@Database(entities = [Crypto::class, Price::class, Wallet::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "crypto.db")
                .build()
                .also { INSTANCE = it }
        }
    }

    abstract fun cryptoDao(): CryptoDao

    abstract fun priceDao(): PriceDao

    abstract fun walletDao(): WalletDao

}