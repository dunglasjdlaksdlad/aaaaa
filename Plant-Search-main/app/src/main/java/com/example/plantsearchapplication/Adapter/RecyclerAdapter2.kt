package com.example.plantsearchapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantsearchapplication.Models.SP
import com.example.plantsearchapplication.R

class RecyclerAdapter2(private val fragment: Fragment, private val spList: ArrayList<SP>): RecyclerView.Adapter<RecyclerAdapter2.ViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener=clickListener
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.image)
        val name : TextView = itemView.findViewById(R.id.name)
        val kingdom : TextView = itemView.findViewById(R.id.kingdom)
        val family : TextView = itemView.findViewById(R.id.family)
        val decription : TextView = itemView.findViewById(R.id.decription)
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter2.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.bbb, parent, false)
        return ViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter2.ViewHolder, position: Int) {
        val currentitem = spList[position]
        Glide.with(fragment)
            .load(currentitem.image)
            .into(holder.image)
        holder.name.text =currentitem.name
        holder.kingdom.text =currentitem.kingdom
        holder.family.text =currentitem.family
        holder.decription.text = currentitem.decription


    }

    override fun getItemCount(): Int {
        return spList.size
    }

}