package com.example.detailsdemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.detailsdemo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_edit.view.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var user :User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        var mainActivityViewModel : MainActivityViewModel = ViewModelProvider(this) [MainActivityViewModel::class.java]

        user = intent.getSerializableExtra("data") as User
        val position = intent.getIntExtra("position",0)
        binding.user = user
        var bmp:Bitmap ?= null
        try {
            bmp = BitmapFactory.decodeByteArray(user.image, 0 , user.image!!.size)
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        imageViewMain.setImageBitmap(bmp)

        edit.setOnClickListener{
            val image = findViewById<ImageView>(R.id.imageViewMain)
            val bitmap:Bitmap = (image.getDrawable() as BitmapDrawable).getBitmap()
            var file:ByteArray ?= null
            try {
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                file = stream.toByteArray()

            } catch ( e:Exception ) {
                e.printStackTrace()
            }

            user.name = nameV.text.toString()
            user.email = emailV.text.toString()
            user.mobile = mobileV.text.toString()
            user.gender = genderV.text.toString()
            user.address = addressV.text.toString()
            user.image = file
            val intent = Intent(this,EditActivity::class.java)

            intent.putExtra("user",user)

            startActivityForResult(intent,10)
        }

        done.setOnClickListener {
            val returnIntent: Intent = Intent(this,ListActivity::class.java)

//            val image = findViewById<ImageView>(R.id.imageViewMain)
//            val bitmap:Bitmap = (image.getDrawable() as BitmapDrawable).getBitmap()

            returnIntent.putExtra("returnData",user)
            returnIntent.putExtra("returnPosition",position)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null){
            if(resultCode == Activity.RESULT_OK ) {

                val u = data.getSerializableExtra("userReturn") as User

                user.name = u.name
                user.email = u.email
                user.mobile = u.mobile
                user.gender = u.gender
                user.address = u.address
                user.image = u.image
                binding.invalidateAll()
                var bmp:Bitmap ?= null
                try {
                    bmp = BitmapFactory.decodeByteArray(user.image, 0 , user.image!!.size)
                }
                catch (e:Exception){
                    e.printStackTrace()
                }

                findViewById<ImageView>(R.id.imageViewMain).setImageBitmap(bmp)

            }
        }
    }
}
