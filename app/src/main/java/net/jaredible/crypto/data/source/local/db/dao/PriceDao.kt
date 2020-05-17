package net.jaredible.crypto.data.source.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import net.jaredible.crypto.data.model.Price

@Dao
interface PriceDao {

    @Query("SELECT * FROM price ORDER BY symbol")
    fun get(): LiveData<List<Price>>

    @Query("SELECT * FROM price WHERE symbol = :symbol ORDER BY symbol")
    fun get(symbol: String): LiveData<Price>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(price: Price)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(prices: List<Price>)

    @Update
    fun update(price: Price)

    @Delete
    fun delete(price: Price)

    @Query("DELETE FROM price")
    fun delete()

}