package net.jaredible.crypto.data.source.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import net.jaredible.crypto.data.model.Price

@Dao
interface PriceDao {

    @Query("SELECT * FROM price ORDER BY crypto_symbol")
    fun get(): LiveData<Price>

    @Query("SELECT * FROM price WHERE crypto_symbol = :cryptoSymbol ORDER BY crypto_symbol")
    fun get(cryptoSymbol: String): LiveData<Price>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(price: Price)

    @Update
    fun update(price: Price)

    @Delete
    fun delete(price: Price)

    @Query("DELETE FROM price")
    fun delete()

}