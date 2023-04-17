package com.mkrlabs.pmisdefence.model

import java.sql.Timestamp


data class Message(val message :String , val messageType: MessageType,val layoutType: LayoutType,val timestamp: Long,val image :Int)
