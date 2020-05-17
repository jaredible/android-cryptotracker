package net.jaredible.crypto.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_settings.*
import net.jaredible.crypto.App
import net.jaredible.crypto.R
import net.jaredible.crypto.data.repository.PreferenceRepository
import net.jaredible.crypto.ui.base.BaseFragment

class SettingsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vMusic.isChecked = PreferenceRepository.isMusicEnabled()
        vMusic.setOnCheckedChangeListener { _, isChecked ->
            PreferenceRepository.setMusicEnabled(isChecked)
            App.context.toggleMusic(isChecked)
        }
    }

}