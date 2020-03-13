package com.example.detailsdemo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import kotlinx.android.synthetic.main.activity_list.*
import java.io.ByteArrayOutputStream
import java.lang.Exception

class ListActivity : AppCompatActivity() {

    lateinit var newUser:User
    var flag =0
    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val toolbar: Toolbar = listBar as Toolbar
        setSupportActionBar(toolbar)

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

        UsersArray.array.add(User("Praneeth","p@g.com","9493220216","male","nzb",file[0]))
        UsersArray.array.add(User("Divya","d@g.com","9493220516","female","hyd",file[1]))
        UsersArray.array.add(User("Haripriya","h@g.com","9498620216","female","sec",file[2]))
        UsersArray.array.add(User("Tyrion","t@g.com","9493236216","male","ban",file[3]))
        UsersArray.array.add(User("Praneeth","p@g.com","9493220216","male","nzb",file[0]))
        UsersArray.array.add(User("Divya","d@g.com","9493220516","female","hyd",file[1]))
        UsersArray.array.add(User("Haripriya","h@g.com","9498620216","female","sec",file[2]))
        UsersArray.array.add(User("Tyrion","t@g.com","9493236216","male","ban",file[3]))
        UsersArray.array.add(User("Praneeth","p@g.com","9493220216","male","nzb",file[0]))
        UsersArray.array.add(User("Divya","d@g.com","9493220516","female","hyd",file[1]))
        UsersArray.array.add(User("Haripriya","h@g.com","9498620216","female","sec",file[2]))
        UsersArray.array.add(User("Tyrion","t@g.com","9493236216","male","ban",file[3]))

        fab.setOnClickListener {
            val ba:ByteArray ?= null
             newUser = User("","","","","",ba)

            val newIntent = Intent(this, EditActivity::class.java)
            newIntent.putExtra("newUser",newUser)
            newIntent.putExtra("callingActivity",1)
            startActivityForResult(newIntent,15)
        }
    }
    lateinit var menu:Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu!!
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var fragment:Fragment
        if(item.title == "grid"){
            item.title = "list"
            item.setIcon(R.drawable.list)
            flag = 1
            fragment = GridFragment()
            //var fm:FragmentManager = getAct getSupportFragmentManager()
            var ft: FragmentTransaction =getSupportFragmentManager().beginTransaction()
            ft.replace(R.id.fragment, fragment)
            ft.commit()
        }
        else{
            item.title = "grid"
            item.setIcon(R.drawable.grid)
            flag = 0
            fragment = ListFragment()
            //var fm:FragmentManager = getAct getSupportFragmentManager()
            var ft: FragmentTransaction =getSupportFragmentManager().beginTransaction()
            ft.replace(R.id.fragment, fragment)
            ft.commit()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Grid","sfs")
        if(requestCode == 15)
        {
            if(data!=null){
                newUser = data.getSerializableExtra("userReturn") as User
                UsersArray.array.add(newUser)
            }
        }
        if(flag == 0)
            customListAdapter.notifyDataSetChanged()
        if(flag == 1)
            customGridAdapter.notifyDataSetChanged()
    }
}