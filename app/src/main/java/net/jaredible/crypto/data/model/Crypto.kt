package net.jaredible.crypto.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import net.jaredible.crypto.App

@Entity(tableName = "crypto")
data class Crypto(
    @PrimaryKey
    @ColumnInfo(name = "symbol")
    val symbol: String,
    @ColumnInfo(name = "name")
    val name: String
) {
    @Ignore
    val logoResId = App.context.resources.getIdentifier(
        "logo_${symbol.toLowerCase()}",
        "drawable",
        App.context.packageName
    )
}