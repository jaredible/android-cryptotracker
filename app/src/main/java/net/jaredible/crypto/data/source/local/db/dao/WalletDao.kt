package net.jaredible.crypto.data.source.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import net.jaredible.crypto.data.model.Wallet

@Dao
interface WalletDao {

    @Query("SELECT * FROM wallet ORDER BY name")
    fun getWallets(): LiveData<List<Wallet>>

    @Query("SELECT * FROM wallet WHERE name = :name")
    fun getWallet(name: String): LiveData<Wallet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wallet: Wallet)

    @Update
    suspend fun update(wallet: Wallet)

    @Delete
    suspend fun delete(wallet: Wallet)

}