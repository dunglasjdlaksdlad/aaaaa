package com.example.plantsearchapplication.Articles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsearchapplication.Adapter.RecyclerAdapter
import com.example.plantsearchapplication.Adapter.RecyclerAdapter2
import com.example.plantsearchapplication.Adapter.RecyclerAdapter_Articles
import com.example.plantsearchapplication.Models.AR
import com.example.plantsearchapplication.Models.SP
import com.example.plantsearchapplication.R

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private lateinit var ar_Recyclerview : RecyclerView
private lateinit var ar_dbref : DatabaseReference
private lateinit var ar_ArrayList : ArrayList<AR>


class ArticlesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ar_Recyclerview = view.findViewById(R.id.recyclerView_ar)
        ar_Recyclerview.layoutManager = LinearLayoutManager(context)
        ar_Recyclerview.setHasFixedSize(true)

        ar_ArrayList = arrayListOf<AR>()
        getARData()


    }
    private fun getARData() {

////                          firebase
        ar_dbref = FirebaseDatabase.getInstance().getReference("Articles")
        ar_dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(AR::class.java)
                        ar_ArrayList.add(user!!)
                    }
                    //                    arRecyclerview.adapter = RecyclerAdapter(arArrayList)
                    var adapter = RecyclerAdapter_Articles(this@ArticlesFragment, ar_ArrayList)
                    ar_Recyclerview.adapter =adapter

                    adapter.setOnItemClickListener(object : RecyclerAdapter_Articles.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val bundle = Bundle()
                            bundle.putString("title", ar_ArrayList[position].title.toString())
                            bundle.putString("user", ar_ArrayList[position].user.toString())
                            bundle.putString("date", ar_ArrayList[position].date.toString())
                            bundle.putString("decription", ar_ArrayList[position].decription.toString())
                            bundle.putString("image_user", ar_ArrayList[position].image_user.toString())
                            bundle.putString("image", ar_ArrayList[position].image.toString())
                            findNavController().navigate(R.id.action_articlesFragment_to_articlesFragment1,bundle)
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