package com.mkrlabs.pmisdefence.model

/*var message :String ,
var messageType: MessageType,
val layoutType: LayoutType,
val timestamp: Long,
val image :String*/

 class Message{
     var message :String =""
     var messageId :String =""
     var messageType: MessageType? = null
     val layoutType: LayoutType? =null
     val timestamp: Long = 0L
     val image :String? = ""


     constructor()
     constructor( message : String,messageId :String, messageType: MessageType,image :String,timestamp : Long)
     constructor( message : String,messageId :String, messageType: MessageType,image :String,timestamp : Long, layoutType: LayoutType)


 }
