package com.example.detailsdemo

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.custome_list.view.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception

class CustomListAdapter(val context:Activity, val data:ArrayList<User>):ArrayAdapter<User>(context, R.layout.custome_list, data){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custome_list, null, true)
        rowView.displayName.text = data[position].name
        rowView.displayGender.text = data[position].gender
        var bmp:Bitmap ?= null
        try {
            bmp = BitmapFactory.decodeByteArray(data[position].image, 0 , data[position].image!!.size)
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        rowView.profilePic.setImageBitmap(bmp)
        return rowView
    }
}