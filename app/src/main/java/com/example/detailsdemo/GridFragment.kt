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
import android.widget.GridView
import android.widget.ListView
import android.widget.Toast

lateinit var customGridAdapter:CustomGridAdapter
class GridFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_grid, container, false)
        Log.d("Fragment","OnCreateView")
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d("Fragment","OnActivityCreated")
        val gridView: GridView = view!!.findViewById(R.id.gridView)
        customGridAdapter = CustomGridAdapter(context!!, UsersArray.array)
        gridView.adapter = customGridAdapter

        gridView.setOnItemClickListener { parent, view, position, id ->

            Toast.makeText(this.context, "Clicked item : $position", Toast.LENGTH_SHORT).show()
            val data: User = parent.getItemAtPosition(position) as User

            val intent = Intent(this.context, MainActivity::class.java)
            intent.putExtra("data", data)
            intent.putExtra("position", position)
            this.startActivityForResult(intent,20)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        customGridAdapter.notifyDataSetChanged()
    }
}
