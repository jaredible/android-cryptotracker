package net.jaredible.crypto

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun stringFrom(@StringRes stringRes: Int, vararg param: String? = emptyArray()) =
    App.context.getString(stringRes, *param)

fun intFrom(@IntegerRes intRes: Int) =
    App.context.resources.getInteger(intRes)

fun colorFrom(@ColorRes colorRes: Int) =
    ContextCompat.getColor(App.context, colorRes)

fun drawableFrom(@DrawableRes drawableRes: Int) =
    ContextCompat.getDrawable(App.context, drawableRes)

fun Context.isConnected(): Boolean {
    return true
}