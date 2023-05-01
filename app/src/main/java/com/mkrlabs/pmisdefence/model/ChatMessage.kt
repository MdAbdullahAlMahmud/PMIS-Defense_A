package com.mkrlabs.pmisdefence.model

data class ChatMessage(
    var  message : String = "",
    var messageId : String = "",
    var senderId  : String= "",
    var  timestamp : Long=0L,
    var type : MessageType = MessageType.TEXT
)
