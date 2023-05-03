package com.mkrlabs.pmisdefence.model

open class Student : User{


       var image : String =""
       var batchId : String = ""
        var type: UserType? =null

    constructor()
     constructor(name:String, email:String, password:String, id :String, uid : String) : super(name,email,password,id,uid)
     constructor(name:String, email:String, password:String, id :String, uid : String , image :String, batchId : String ,type: UserType) : this(name,email,password,id,uid){
                 this.image = image
                 this.batchId = batchId
                 this.type = type
             }


    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

 }


     /**
     var name:String,
    var email :String   ,
    var password:String ,
    var id :String ,
    var uid:String ,
    var  designation:String ,
    var phone:String,
    var image: String ,
    var batchId: String

    **/

/**

val name:String,
val email :String,
val  password:String,
val id :String,
var uid:String

 **/