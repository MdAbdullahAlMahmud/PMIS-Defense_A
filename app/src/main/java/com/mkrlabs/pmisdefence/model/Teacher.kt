package com.mkrlabs.pmisdefence.model


class Teacher(
    name:String,
    email :String,
    password:String,
    id :String,
    uid:String,

    var  designation: String,
    var phone: String,
    var image: String,
    var room: String,
    var type: UserType



    ): User(name,email,password,id,uid) {
}