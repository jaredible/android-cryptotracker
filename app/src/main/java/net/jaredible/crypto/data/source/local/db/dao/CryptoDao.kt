package net.jaredible.crypto.data.source.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import net.jaredible.crypto.data.model.Crypto

@Dao
interface CryptoDao {

    @Query("SELECT * FROM crypto ORDER BY symbol")
    fun get(): LiveData<Crypto>

    @Query("SELECT * FROM crypto WHERE symbol = :symbol ORDER BY symbol")
    fun get(symbol: String): LiveData<Crypto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(crypto: Crypto)

    @Update
    fun update(crypto: Crypto)

    @Delete
    fun delete(crypto: Crypto)

    @Query("DELETE FROM crypto")
    fun delete()

}