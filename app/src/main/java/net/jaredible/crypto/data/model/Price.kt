package net.jaredible.crypto.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "price")
@Parcelize
data class Price(
    @PrimaryKey
    @ColumnInfo(name = "symbol")
    @SerializedName("symbol")
    val symbol: String,
    @ColumnInfo(name = "price")
    @SerializedName("price")
    val price: Double = 0.0
) : Parcelable