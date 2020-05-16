package net.jaredible.crypto.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import net.jaredible.crypto.R
import net.jaredible.crypto.ui.base.BaseActivity

class SettingsActivity : BaseActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

}