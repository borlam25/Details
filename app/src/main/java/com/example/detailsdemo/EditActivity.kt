package com.example.detailsdemo

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.detailsdemo.databinding.ActivityEditBinding
import kotlinx.android.synthetic.main.activity_edit.*
import java.io.*
import java.util.*

@Suppress("DEPRECATION")
class EditActivity : AppCompatActivity() {
    private var btn: Button? = null
    private var imageview: ImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2
    lateinit var bitmapRes:Bitmap
    private var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val binding :ActivityEditBinding = DataBindingUtil.setContentView(this,R.layout.activity_edit)
        val user = intent.getSerializableExtra("user") as User
        binding.user=user

        if (user.gender == "male") {
            radioGroup.check(R.id.maleButton)
        } else {
            radioGroup.check(R.id.femaleButton)
        }

        var bmp:Bitmap ?= null
        val file:String ?= intent.getStringExtra("imageView")
        try{
            val fis: FileInputStream = this.openFileInput(file)
            bmp = BitmapFactory.decodeStream(fis)
            fis.close()
        }
        catch (e: java.lang.Exception){
            e.printStackTrace()
        }
        findViewById<ImageView>(R.id.imageView).setImageBitmap(bmp)


        save.setOnClickListener {
            user.name = nameText.text.toString()
            user.email = emailText.text.toString()
            user.mobile = mobileText.text.toString()
            user.address = addressText.text.toString()
            val genderResult: String

            if (maleButton.isChecked) {
                genderResult = "male"
            } else {
                genderResult = "female"
            }

            user.gender = genderResult

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userReturn",user)


            if(flag == 0) {
                val image = findViewById<ImageView>(R.id.imageView)
                bitmapRes = (image.getDrawable() as BitmapDrawable).getBitmap()
            }
            try {
                //Write file
                val filename = "bitmap.png"
                val stream: FileOutputStream =
                    this.openFileOutput(filename, Context.MODE_PRIVATE)
                bitmapRes.compress(Bitmap.CompressFormat.PNG, 100, stream)

                //Cleanup
                stream.close()
                bitmapRes.recycle()

                //Pop intent

                intent.putExtra("image", filename)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        btn = findViewById<View>(R.id.button2) as Button
        imageview = findViewById<View>(R.id.imageView) as ImageView

        btn!!.setOnClickListener {

            showPictureDialog()
        }
    }
    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/

        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    bitmapRes = bitmap
                    val path = saveImage(bitmap)
                    Log.d("path",path)
                    Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
                    imageview!!.setImageBitmap(bitmap)

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }
            flag = 1
        }
        else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            imageview!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .timeInMillis).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.path),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.absolutePath)

            return f.absolutePath
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    companion object {
        private const val IMAGE_DIRECTORY = "/demonuts"
    }
}
