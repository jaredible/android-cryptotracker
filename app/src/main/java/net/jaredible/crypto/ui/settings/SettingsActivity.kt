package net.jaredible.crypto.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import kotlinx.android.synthetic.main.activity_settings.*
import net.jaredible.crypto.App
import net.jaredible.crypto.R
import net.jaredible.crypto.data.repository.PreferenceRepository
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

        setSupportActionBar(vToolbar)
        vToolbar.setNavigationIcon(R.drawable.ic_close)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        vMusic.isChecked = PreferenceRepository.isMusicEnabled()
        vMusic.setOnCheckedChangeListener { _, isChecked ->
            PreferenceRepository.setMusicEnabled(isChecked)
            App.context.toggleMusic(isChecked)
        }

        if (savedInstanceState == null) {
            vRoot.apply {
                visibility = View.INVISIBLE
                if (viewTreeObserver.isAlive) {
                    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            viewTreeObserver.removeOnGlobalLayoutListener(this)
                            startRevealTransition(vRoot)
                        }
                    })
                }
            }
        }
    }

}