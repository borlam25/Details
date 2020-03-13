package com.example.detailsdemo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast

lateinit var customListAdapter:CustomListAdapter
class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        Log.d("Fragment","OnCreateView")
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d("Fragment","OnActivityCreated")
        val listView: ListView = view!!.findViewById(R.id.listView)
        customListAdapter = CustomListAdapter(context!!, UsersArray.array)
        listView.adapter = customListAdapter

        listView.setOnItemClickListener { parent, view, position, id ->

            Toast.makeText(this.context, "Clicked item : $position", Toast.LENGTH_SHORT).show()
            val data: User = parent.getItemAtPosition(position) as User

            val intent = Intent(this.context, MainActivity::class.java)
            intent.putExtra("data", data)
            intent.putExtra("position", position)
            this.startActivityForResult(intent,12)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        customListAdapter.notifyDataSetChanged()
    }
}