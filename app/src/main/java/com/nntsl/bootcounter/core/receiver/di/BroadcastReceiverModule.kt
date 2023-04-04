package com.nntsl.bootcounter.core.receiver.di

import com.nntsl.bootcounter.core.receiver.BootEventReceiver
import com.nntsl.bootcounter.core.receiver.HiltBroadcastReceiver
import com.nntsl.bootcounter.core.receiver.NotificationPublisher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface BroadcastReceiverModule {

    @Binds
    fun bindsBootEventBroadcastReceiver(
        bootEventReceiver: BootEventReceiver
    ): HiltBroadcastReceiver

    @Binds
    fun bindsNotificationPublisher(
        notificationPublisher: NotificationPublisher
    ): HiltBroadcastReceiver
}
