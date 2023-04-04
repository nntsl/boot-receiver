package com.nntsl.bootcounter.core.receiver

import android.content.Context
import android.content.Intent
import com.nntsl.bootcounter.core.domain.usecase.InsertBootEventUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootEventReceiver @Inject constructor(
    private val insertBootEventUseCase: InsertBootEventUseCase
) : HiltBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {

            val timestamp = System.currentTimeMillis()

            CoroutineScope(Dispatchers.IO).launch {
                insertBootEventUseCase(timestamp)
            }
        }
    }
}
