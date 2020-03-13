package com.example.detailsdemo
class UsersArray {
    companion object {
        var array = ArrayList<User>()
            get() = field
            set(value) {
                field = value
            }
    }
}