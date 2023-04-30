package com.mkrlabs.pmisdefence.model

import java.sql.Timestamp


data class Message(
    var message :String ,
    var messageType: MessageType,
    val layoutType: LayoutType,
    val timestamp: Long,
    val image :Int)
