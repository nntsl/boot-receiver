package com.nntsl.bootcounter.core.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.nntsl.bootcounter.R
import com.nntsl.bootcounter.core.domain.usecase.GetBootEventsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationPublisher : HiltBroadcastReceiver() {

    @Inject
    lateinit var getSavedBootEventsUseCase: GetBootEventsUseCase

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        CoroutineScope(Dispatchers.IO).launch {

            getSavedBootEventsUseCase()
                .collect { events ->
                    val notificationContent = when (events.size) {
                        0 -> "No boots detected"
                        1 -> "The boot was detected with the timestamp = ${events[0].timestamp}"
                        else -> {
                            val timeDelta = events.last().timestamp - events[events.size - 2].timestamp
                            "Last boots time delta = $timeDelta"
                        }
                    }

                    showNotification(context, notificationContent)
                }
        }
    }

    private fun showNotification(context: Context, content: String) {
        val channelId = "boot_event_notification_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, "Boot Event Notifications", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Boot Event Info")
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}
