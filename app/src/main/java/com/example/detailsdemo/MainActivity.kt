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
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var binding:ActivityMainBinding ?= null
    var user :User ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        var mainActivityViewModel : MainActivityViewModel = ViewModelProvider(this) [MainActivityViewModel::class.java]

        binding?.user = mainActivityViewModel.user
        user = mainActivityViewModel.user
        edit.setOnClickListener{

            user?.name = nameV.text.toString()
            user?.email = emailV.text.toString()
            user?.mobile = mobileV.text.toString()
            user?.gender = genderV.text.toString()
            user?.address = addressV.text.toString()

            val intent = Intent(this,EditActivity::class.java)

            intent.putExtra("user",user)
            val image = findViewById<ImageView>(R.id.imageView)
            val bitmap:Bitmap = (image.getDrawable() as BitmapDrawable).getBitmap()

            try {
                //Write file
                val  filename = "bitmap.png"
                val stream: FileOutputStream = this.openFileOutput(filename, Context.MODE_PRIVATE)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                //Cleanup
                stream.close()
                bitmap.recycle()

                //Pop intent

                intent.putExtra("imageView", filename)

            } catch ( e:Exception ) {
                e.printStackTrace()
            }

            startActivityForResult(intent,10)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null){
            if(resultCode == Activity.RESULT_OK ) {
                val u:User = data.getSerializableExtra("userReturn") as User
                user?.name = u.name
                user?.email = u.email
                user?.gender = u.gender
                user?.mobile = u.mobile
                user?.address = u.address
                binding?.invalidateAll()

                var bmp:Bitmap ?= null
                val file:String = data.extras?.get("image").toString()
                try{
                    val fis:FileInputStream = this.openFileInput(file)
                    bmp = BitmapFactory.decodeStream(fis)
                    fis.close()
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
                findViewById<ImageView>(R.id.imageView).setImageBitmap(bmp)

            }
        }
    }
}
