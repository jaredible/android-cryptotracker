package net.jaredible.crypto.data.repository

import androidx.preference.PreferenceManager
import net.jaredible.crypto.App

object PreferenceRepository {

    private val prefDb by lazy {
        PreferenceManager.getDefaultSharedPreferences(App.context)
    }

}