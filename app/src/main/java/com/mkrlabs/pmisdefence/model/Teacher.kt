package com.mkrlabs.pmisdefence.model


 class Teacher
    (
    name:String,
    email :String,
    password:String,
    id :String,
    uid:String,
    val  designation:String,
    val phone:String,
    val image: String,
    val room: String,



    ): User(name,email,password,id,uid) {
}