package com.nntsl.bootcounter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nntsl.bootcounter.core.receiver.BootEventReceiver
import com.nntsl.bootcounter.core.receiver.NotificationPublisher
import com.nntsl.bootcounter.core.domain.model.BootEvent
import com.nntsl.bootcounter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var bootEventReceiver: BootEventReceiver

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerReceiver(bootEventReceiver, IntentFilter(Intent.ACTION_BOOT_COMPLETED))
        scheduleRecurringNotificationTask()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is LatestBootEventsUiState.Success -> {
                            updateBootEventsDisplay(uiState.events)
                        }
                        is LatestBootEventsUiState.Error -> {
                            binding.bootEventsTextView.text = getString(R.string.error)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bootEventReceiver)
    }

    private fun updateBootEventsDisplay(bootEvents: List<BootEvent>) {
        var stringBuilder = ""
        if (bootEvents.isEmpty()) {
            stringBuilder = getString(R.string.no_boots_detected)
        } else {
            bootEvents.forEachIndexed { index, bootEvent ->
                stringBuilder += "${index + 1} - ${bootEvent.timestamp}\n"
            }
        }
        binding.bootEventsTextView.text = stringBuilder
    }

    private fun scheduleRecurringNotificationTask() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(this, NotificationPublisher::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val interval = TimeUnit.MINUTES.toMillis(15)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + interval,
            interval,
            pendingIntent
        )
    }
}
