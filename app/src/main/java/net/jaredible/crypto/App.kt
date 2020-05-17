package net.jaredible.crypto

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import net.jaredible.crypto.data.repository.CryptoRepository
import net.jaredible.crypto.data.repository.PreferenceRepository
import net.jaredible.crypto.data.repository.PriceRepository
import net.jaredible.crypto.ui.MusicService

class App : Application() {

    companion object {
        lateinit var context: App private set
        private const val CHANNEL_ID = "net.jaredible.crypto.CHANNEL_ID"
        private const val NOTIFICATION_CRYPTOS_UPDATED_ID = 1
        private const val NOTIFICATION_PRICES_UPDATED_ID = 2
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        initMusic()
        initDatabase()
        initNotificationChannels()

        CryptoRepository.updateCryptos()
        PriceRepository.updatePrices()
    }

    private fun initMusic() {
        toggleMusic(PreferenceRepository.isMusicEnabled())
    }

    private fun initDatabase() {}

    fun toggleMusic(enabled: Boolean) {
        val intent = Intent(this, MusicService::class.java)
        if (enabled) startService(intent)
        else stopService(intent)
    }

    private fun initNotificationChannels() {
        createNotificationChannel(getString(R.string.channel_name), getString(R.string.channel_description))
    }

    private fun createNotificationChannel(name: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply { this.description = description }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(id: Int, @DrawableRes icon: Int, title: String, content: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(id, builder.build())
        }
    }

    fun notifyCryptosUpdated() {
        val title = getString(R.string.notification_cryptos_updated_title)
        val content = getString(R.string.notification_cryptos_updated_content)
        showNotification(NOTIFICATION_CRYPTOS_UPDATED_ID, R.drawable.bg_splash, title, content)
    }

    fun notifyPricesUpdated() {
        val title = getString(R.string.notification_prices_updated_title)
        val content = getString(R.string.notification_prices_updated_content)
        showNotification(NOTIFICATION_PRICES_UPDATED_ID, R.drawable.bg_splash, title, content)
    }

}