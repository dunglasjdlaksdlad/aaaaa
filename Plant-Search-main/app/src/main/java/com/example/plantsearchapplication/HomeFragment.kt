package com.example.plantsearchapplication

import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.plantsearchapplication.R
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsearchapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: ParentAdapter
    val REQUEST_IMAGE_CAPTURE=100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.species.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_speciesFragment)
        }
//        binding.species.setOnClickListener {
//            findNavController().navigate(R.id.action_speciesFragment_to_speciesFragment2)
//        }

        binding.articles.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_articlesFragment)
        }

        binding.addingNew.setOnClickListener {
            findNavController().navigate(R.id.addnew)

        }



        // Set up RecyclerView and Adapter
        val mRecyclerView: RecyclerView = view.findViewById(R.id.parent_recyclerview)
        setAdapter(mRecyclerView)

    }

    private fun setAdapter(recyclerView: RecyclerView) {
        val mParentList = ArrayList<Parent>()

        // Initializing all lists
        val plantTypesList = ArrayList<Plant>()
        val photographyList = ArrayList<Plant>()

        plantTypesList.add(Plant("Cactus", "https://static.vinwonders.com/production/cay-xuong-rong-5.jpg"))
        plantTypesList.add(Plant("Eagletree", "https://iuhoa.com/wp-content/uploads/2021/07/meo-chua-to-dia-bang-la-bang.jpg"))

        photographyList.add(Plant("Rose", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSM_DT909JDRNueZ56Ymq09aFSVH8cAadAhwg&usqp=CAU"))
        photographyList.add(Plant("Eagletree", "https://iuhoa.com/wp-content/uploads/2021/07/meo-chua-to-dia-bang-la-bang.jpg"))

        // Add the lists to the parent list
        mParentList.add(Parent("Plant Types",  plantTypesList))
        mParentList.add(Parent("Photography", photographyList))

        mAdapter = ParentAdapter(requireContext(), mParentList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = mAdapter
    }


}

