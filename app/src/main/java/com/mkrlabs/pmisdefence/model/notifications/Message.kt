package com.mkrlabs.pmisdefence.model.notifications

data class Message(
    val notification: Notification,
    val topic: String
)