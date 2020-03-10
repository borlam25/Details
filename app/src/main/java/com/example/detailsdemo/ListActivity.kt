package com.example.detailsdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.lang.Exception

class ListActivity : AppCompatActivity() {
    lateinit var customListAdapter:CustomListAdapter
    val array = ArrayList<User>()
    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val listView: ListView = this.findViewById(R.id.listView)


        customListAdapter = CustomListAdapter(this, array)
        val bitmap = arrayOfNulls<Bitmap>(4)
        bitmap[0] = BitmapFactory.decodeResource(resources,R.drawable.user1)
        bitmap[1] = BitmapFactory.decodeResource(resources,R.drawable.user2)
        bitmap[2] = BitmapFactory.decodeResource(resources,R.drawable.user3)
        bitmap[3] = BitmapFactory.decodeResource(resources,R.drawable.user4)

        val file = arrayOfNulls<ByteArray>(4)
        for(i in 0..4){
            try {
                //Write file
                val stream = ByteArrayOutputStream()
                bitmap[i]?.compress(Bitmap.CompressFormat.PNG, 100, stream)
                file[i] = stream.toByteArray()
                //Cleanup

                //Pop intent

            } catch ( e:Exception ) {
                e.printStackTrace()
            }
        }

        array.add(User("Praneeth","p@g.com","9493220216","male","nzb",file[0]))
        array.add(User("Divya","d@g.com","9493220516","female","hyd",file[1]))
        array.add(User("Haripriya","h@g.com","9498620216","female","sec",file[2]))
        array.add(User("Tyrion","t@g.com","9493236216","male","ban",file[3]))

        listView.adapter = customListAdapter

        listView.setOnItemClickListener { parent, view, position, id ->

            Toast.makeText(this, "Clicked item : $position",Toast.LENGTH_SHORT).show()
            val data:User = parent.getItemAtPosition(position) as User

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("data", data)
            intent.putExtra("position", position)

            this.startActivityForResult(intent,12)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Activity","dvghad")
        if(data != null){
           val pos = data.getIntExtra("returnPosition",0)
            val user = data.getSerializableExtra("returnData") as User
            val dataU = customListAdapter.getItem(pos)
            dataU?.name = user.name
            dataU?.email = user.email
            dataU?.mobile = user.mobile
            dataU?.gender = user.gender
            dataU?.address = user.address
            dataU?.image = user.image
            Log.d("Activity",dataU?.name)
            if (dataU != null) {
                array.set(pos,dataU)
            }
            customListAdapter.notifyDataSetChanged()
//            customListAdapter = CustomListAdapter(this, array)
        }


    }
}