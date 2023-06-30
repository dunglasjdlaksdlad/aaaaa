package com.example.plantsearchapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SectionIndexer
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsearchapplication.Models.SP
import com.example.plantsearchapplication.R
import java.util.HashMap
import java.util.Locale

class RecyclerAdapter(private val fragment: Fragment, private val sp_1_list: ArrayList<SP>):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(), SectionIndexer {

    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener=clickListener
    }

    class ViewHolder(itemView: View, clickListener: RecyclerAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val sp_1 : TextView = itemView.findViewById(R.id.text1)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout_sp1, parent, false)
        return RecyclerAdapter.ViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val currentitem = sp_1_list[position]
        holder.sp_1.text = currentitem.sp
    }

    override fun getItemCount(): Int {
        return sp_1_list.size
    }


    private val mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#"
    private var sectionsTranslator = HashMap<Int, Int>()
    private var mSectionPositions: ArrayList<Int>? = null

    override fun getSectionForPosition(position: Int): Int {
        return 0
    }

    override fun getSections(): Array<String> {
        val sections: MutableList<String> = ArrayList(27)
        val alphabetFull = ArrayList<String>()
        mSectionPositions = ArrayList()
        run {
            var i = 0
            val size = sp_1_list!!.size
            while (i < size) {
                val section = sp_1_list[i].toString().uppercase(Locale.getDefault())
                if (!sections.contains(section)) {
                    sections.add(section)
                    mSectionPositions?.add(i)
                }
                i++
            }
        }
        for (element in mSections) {
            alphabetFull.add(element.toString())
        }
//        sectionsTranslator = sectionsHelper(sections, alphabetFull)
        return alphabetFull.toTypedArray()
    }

    override fun getPositionForSection(sectionIndex: Int): Int {
        return mSectionPositions!![sectionsTranslator[sectionIndex]!!]
    }

}