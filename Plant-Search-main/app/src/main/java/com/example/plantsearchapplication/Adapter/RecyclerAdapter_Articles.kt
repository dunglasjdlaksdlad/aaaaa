package com.example.plantsearchapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantsearchapplication.Models.AR
import com.example.plantsearchapplication.Models.SP
import com.example.plantsearchapplication.R

class RecyclerAdapter_Articles(private val fragment: Fragment, private val ar_list: ArrayList<AR>): RecyclerView.Adapter<RecyclerAdapter_Articles.ViewHolder>() {

    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener=clickListener
    }
    class ViewHolder(itemView: View, clickListener: RecyclerAdapter_Articles.onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val title_ar : TextView = itemView.findViewById(R.id.title_ar)
        val user_ar : TextView = itemView.findViewById(R.id.user_ar)
        val date_ar : TextView = itemView.findViewById(R.id.data_ar)
        val deccription_ar : TextView = itemView.findViewById(R.id.decription_ar)
        val image_ar : ImageView = itemView.findViewById(R.id.image_ar)
        val image_user : ImageView = itemView.findViewById(R.id.image_user_ar)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter_Articles.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.aaa, parent, false)
        return RecyclerAdapter_Articles.ViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter_Articles.ViewHolder, position: Int) {
        val currentitem = ar_list[position]
        holder.title_ar.text = currentitem.title
        holder.user_ar.text = currentitem.user
        holder.date_ar.text = currentitem.date
        holder.deccription_ar.text = currentitem.decription
        Glide.with(fragment)
            .load(currentitem.image)
            .into(holder.image_ar)
        Glide.with(fragment)
            .load(currentitem.image_user)
            .into(holder.image_user)
        holder.date_ar.setOnClickListener{

        }

    }

    override fun getItemCount(): Int {
        return ar_list.size
    }
}