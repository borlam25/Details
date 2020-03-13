package com.example.detailsdemo

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.custom_grid.view.*
import kotlinx.android.synthetic.main.custome_list.view.*
import kotlinx.android.synthetic.main.custome_list.view.displayGender
import kotlinx.android.synthetic.main.custome_list.view.displayName
import kotlinx.android.synthetic.main.custome_list.view.profilePic
import java.lang.Exception

class CustomGridAdapter(context: Context, val data:ArrayList<User>):
    ArrayAdapter<User>(context, R.layout.custom_grid, data) {
    val inflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rowView: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            rowView = inflater.inflate(R.layout.custom_grid, parent, false)

            viewHolder = ViewHolder(rowView)
            rowView.tag = viewHolder

        } else {
            rowView = convertView
            viewHolder = rowView.tag as ViewHolder
        }

        viewHolder.name.text = data[position].name
        viewHolder.gender.text = data[position].gender
        if(data[position].gender == "female"){
            rowView.card.setBackgroundColor(Color.rgb(210,210,210))
        }
        var bmp: Bitmap? = null
        try {
            bmp =
                BitmapFactory.decodeByteArray(data[position].image, 0, data[position].image!!.size)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        viewHolder.image.setImageBitmap(bmp)

        rowView.deleteBtn.setOnClickListener {
            val builder = AlertDialog.Builder(context)

            // Set the alert dialog title
            builder.setTitle("Delete Record")

            // Display a message on alert dialog
            builder.setMessage("Do you want to delete "+UsersArray.array.get(position).name+" from List ?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("DELETE"){dialog, which ->
                UsersArray.array.removeAt(position)
                notifyDataSetChanged()
                Toast.makeText(context,"Record deleted", Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("CANCEL"){dialog,which ->
                Toast.makeText(context,"Cancel Pressed", Toast.LENGTH_SHORT).show()
            }

            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()

        }

        return rowView
    }
    private class ViewHolder(view:View){
        val name = view.displayName
        val gender = view.displayGender
        val image = view.profilePic
    }
}