package com.mkrlabs.pmisdefence.model


open class Teacher : User{



    var image: String? =null
    var room: String? =null
    var type: UserType? =null

    constructor()
    constructor(name:String, email:String, password:String, id :String, uid : String) : super(name,email,password,id,uid)
    constructor(name:String, email:String, password:String, id :String, uid : String , image :String, room : String ,type: UserType) : this(name,email,password,id,uid){
        this.image = image
        this.room = room
        this.type = type
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}