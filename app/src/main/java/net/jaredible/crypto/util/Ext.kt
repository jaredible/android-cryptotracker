package net.jaredible.crypto.util

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import net.jaredible.crypto.App
import net.jaredible.crypto.data.repository.PreferenceRepository
import java.text.DecimalFormat

// Resources
fun stringFrom(@StringRes stringRes: Int, vararg param: String? = emptyArray()) =
    App.context.getString(stringRes, *param)

fun intFrom(@IntegerRes intRes: Int) =
    App.context.resources.getInteger(intRes)

fun colorFrom(@ColorRes colorRes: Int) =
    ContextCompat.getColor(App.context, colorRes)

fun drawableFrom(@DrawableRes drawableRes: Int) =
    ContextCompat.getDrawable(App.context, drawableRes)

// Formatting
fun getCryptoFormat() =
    DecimalFormat().apply {
        maximumFractionDigits = 8
        groupingSize = 3
        isGroupingUsed = true
        decimalFormatSymbols.apply {
            decimalSeparator = '.'
            groupingSeparator = Character.MIN_VALUE
            decimalFormatSymbols = this
        }
    }

fun getCurrencyFormat() =
    DecimalFormat().apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
        currency = PreferenceRepository.getCurrency()
    }

// LiveData
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}