package com.example.plantsearchapplication.Species

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsearchapplication.Adapter.RecyclerAdapter
import com.example.plantsearchapplication.Models.SP
import com.example.plantsearchapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


private lateinit var sp_1_Recyclerview : RecyclerView
private lateinit var sp_1_dbref : DatabaseReference
private lateinit var sp_1_ArrayList : ArrayList<SP>

class SpeciesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_species, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp_1_Recyclerview = view.findViewById(R.id.recyclerView_sp1)
        sp_1_Recyclerview.layoutManager = LinearLayoutManager(context)
        sp_1_Recyclerview.setHasFixedSize(true)


        sp_1_ArrayList = arrayListOf<SP>()
        getARData()


    }

    private fun getARData() {

////                          firebase
        sp_1_dbref = FirebaseDatabase.getInstance().getReference("Species")
        sp_1_dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val sp= userSnapshot.child("sp").value.toString()
                        val user = SP(sp = sp)
//                        val user = userSnapshot.getValue(SP::class.java)
                        sp_1_ArrayList.add(user!!)
                    }
                    //                    arRecyclerview.adapter = RecyclerAdapter(arArrayList)
                    var adapter = RecyclerAdapter(this@SpeciesFragment, sp_1_ArrayList)

                    sp_1_Recyclerview.adapter =adapter


                    adapter.setOnItemClickListener(object : RecyclerAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val bundle = Bundle()
                            bundle.putString("sp", sp_1_ArrayList[position].sp.toString())
                            findNavController().navigate(R.id.action_speciesFragment_to_speciesFragment2,bundle)
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