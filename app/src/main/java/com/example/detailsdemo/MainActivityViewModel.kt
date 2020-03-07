package com.example.detailsdemo

import androidx.lifecycle.ViewModel

class MainActivityViewModel:ViewModel(){
    val user:User
    init{
        user=User("","","","","")
    }
}