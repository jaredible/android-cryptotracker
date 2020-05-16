package net.jaredible.crypto.data.source.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import net.jaredible.crypto.data.model.Wallet

@Dao
interface WalletDao {

    @Query("SELECT * FROM wallet ORDER BY name")
    fun get(): LiveData<List<Wallet>>

    @Query("SELECT * FROM wallet WHERE name = :name")
    fun get(name: String): LiveData<Wallet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wallet: Wallet)

    @Update
    fun update(wallet: Wallet)

    @Delete
    fun delete(wallet: Wallet)

    @Query("DELETE FROM wallet")
    fun delete()

}