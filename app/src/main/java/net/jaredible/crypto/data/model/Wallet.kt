package net.jaredible.crypto.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "wallet",
    foreignKeys = [
        ForeignKey(
            entity = Crypto::class,
            parentColumns = ["symbol"],
            childColumns = ["crypto_symbol"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Wallet(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "crypto_symbol")
    val cryptoSymbol: String,
    @ColumnInfo(name = "balance")
    val balance: Int
)