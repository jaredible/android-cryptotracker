package net.jaredible.crypto.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "price",
    foreignKeys = [
        ForeignKey(
            entity = Crypto::class,
            parentColumns = ["symbol"],
            childColumns = ["crypto_symbol"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Price(
    @PrimaryKey
    @ColumnInfo(name = "crypto_symbol")
    val cryptoSymbol: String,
    @ColumnInfo(name = "price_btc")
    val priceBtc: Int,
    @ColumnInfo(name = "price_usd")
    val priceUsd: Int
)