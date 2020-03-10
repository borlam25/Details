package com.example.detailsdemo

import java.io.Serializable

data class User(var name:String?, var email:String?, var mobile:String?, var gender:String?, var address:String?, var image: ByteArray?):Serializable