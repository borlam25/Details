package com.example.detailsdemo

import androidx.lifecycle.ViewModel

class MainActivityViewModel:ViewModel(){
    val user:User
    init{
        val ba:ByteArray = byteArrayOf()
        user=User("","","","","",ba)
    }
}