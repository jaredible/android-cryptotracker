package net.jaredible.crypto.data.repository

import androidx.preference.PreferenceManager
import net.jaredible.crypto.App
import net.jaredible.crypto.util.Const
import java.util.*

object PreferenceRepository {

    private val prefs by lazy {
        PreferenceManager.getDefaultSharedPreferences(App.context)
    }

    fun getCurrency(): Currency = Currency.getInstance(Const.DEFAULT_CURRENCY)

    fun isMusicEnabled() = prefs.getBoolean(Const.PREF_MUSIC, false)

    fun setMusicEnabled(enabled: Boolean) = prefs.edit().putBoolean(Const.PREF_MUSIC, enabled).apply()

}