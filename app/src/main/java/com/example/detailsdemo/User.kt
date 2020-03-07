package com.example.detailsdemo

import android.os.Parcelable
import java.io.Serializable

data class User(var name:String?, var email:String?, var mobile:String?, var gender:String?, var address:String?):Serializable