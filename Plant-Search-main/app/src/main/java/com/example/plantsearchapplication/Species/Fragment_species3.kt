package com.example.plantsearchapplication.Species

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantsearchapplication.Models.SP
import com.example.plantsearchapplication.R
import com.google.firebase.database.DatabaseReference


private lateinit var image :String
private lateinit var name :String
private lateinit var kingdom :String
private lateinit var family :String
private lateinit var decription :String
class fragment_species3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_species3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = arguments?.getString("sp").toString()

        image =arguments?.getString("image").toString()
        name =arguments?.getString("name").toString()
        kingdom =arguments?.getString("kingdom").toString()
        family =arguments?.getString("family").toString()
        decription =arguments?.getString("decription").toString()

        val image_3=view.findViewById<ImageView>(R.id.image)
        val name_3=view.findViewById<TextView>(R.id.title)
        val kingdom_3=view.findViewById<TextView>(R.id.kingdom)
        val family_3 =view.findViewById<TextView>(R.id.family)
        val decription_3 =view.findViewById<TextView>(R.id.decription)


        name_3.text="$name"
        kingdom_3.text="$kingdom"
        family_3.text="$family"
        decription_3.text="$decription"
        Glide.with(this)
            .load(image)
            .into(image_3)


//        spRecyclerview2 = view.findViewById(R.id.recyclerView_sp2)
//        spRecyclerview2.layoutManager = LinearLayoutManager(context)
//        spRecyclerview2.setHasFixedSize(true)
//
//        spArrayList2 = arrayListOf<SP>()

    }

}