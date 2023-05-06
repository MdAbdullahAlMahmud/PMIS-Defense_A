package com.mkrlabs.pmisdefence.model
open class User{
     var name:String= ""
     var email :String= ""
     var  password:String= ""
     var id :String= ""
     var uid:String= ""

     constructor()

     constructor(name: String,email: String , password : String, id : String, uid : String){
         this.name = name
         this.email = email
         this.password = password
         this.id = id
         this.uid = uid
     }

 }


