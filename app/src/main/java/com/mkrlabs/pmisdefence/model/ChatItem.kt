package com.mkrlabs.pmisdefence.model

import java.io.Serializable

data class ChatItem (
   var name : String = "",
   var uid : String = "",
   var role : String = "",
   var image : String = ""
) : Serializable {
}