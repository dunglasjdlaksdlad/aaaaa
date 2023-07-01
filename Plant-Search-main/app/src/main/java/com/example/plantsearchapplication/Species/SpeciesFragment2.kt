package com.example.plantsearchapplication.Species

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsearchapplication.Adapter.RecyclerAdapter2
import com.example.plantsearchapplication.Models.SP
import com.example.plantsearchapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.navigation.fragment.findNavController
import android.util.Base64
private lateinit var spRecyclerview2 : RecyclerView
private lateinit var dbref2 : DatabaseReference
private lateinit var spArrayList2 : ArrayList<SP>
lateinit var sp :String
class SpeciesFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_species2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = arguments?.getString("sp").toString()
        val sp_2 =view.findViewById<TextView>(R.id.sp_2)
        sp_2.text="$sp"
//        tdetails.text="$details"
//        ttitles.text="$titles"
//        Glide.with(this)
//            .load(image)
//            .into(timage)


        spRecyclerview2 = view.findViewById(R.id.recyclerView_sp2)
        spRecyclerview2.layoutManager = LinearLayoutManager(context)
        spRecyclerview2.setHasFixedSize(true)

        spArrayList2 = arrayListOf<SP>()
        getARData(sp)
    }
    private fun getARData( sp:String) {

////                          firebase

        dbref2 = FirebaseDatabase.getInstance().getReference("Species").child(sp)
        dbref2.child(sp).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(SP::class.java)
                        spArrayList2.add(user!!)
                    }
                    var adapter =RecyclerAdapter2(this@SpeciesFragment2,spArrayList2)
                    spRecyclerview2.adapter =adapter

                    adapter.setOnItemClickListener(object : RecyclerAdapter2.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val bundle = Bundle()
                            bundle.putString("image", spArrayList2[position].image.toString())
                            bundle.putString("name", spArrayList2[position].name.toString())
                            bundle.putString("kingdom", spArrayList2[position].kingdom.toString())
                            bundle.putString("family", spArrayList2[position].family.toString())
                            bundle.putString("decription", spArrayList2[position].decription.toString())
                            findNavController().navigate(R.id.action_speciesFragment2_to_fragment_species3,bundle)
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}