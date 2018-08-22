package com.rafaellyra.pinkyratasks.eventbus.base

import org.greenrobot.eventbus.EventBus

object AppEventBus {
    val default: EventBus = EventBus.getDefault()
    val repository: EventBus = EventBus.builder().build()
}
