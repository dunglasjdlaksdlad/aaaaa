package com.example.plantsearchapplication.Articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.plantsearchapplication.R

private lateinit var title :String
private lateinit var user :String
private lateinit var date :String
private lateinit var decription :String
private lateinit var image_user :String
private lateinit var image :String

class ArticlesFragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articles1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title =arguments?.getString("title").toString()
        user =arguments?.getString("user").toString()
        date =arguments?.getString("date").toString()
        decription =arguments?.getString("decription").toString()
        image_user =arguments?.getString("image_user").toString()
        image =arguments?.getString("image").toString()


        val title_1=view.findViewById<TextView>(R.id.title_ar)
        val user_1=view.findViewById<TextView>(R.id.user_ar)
        val date_1=view.findViewById<TextView>(R.id.data_ar)
        val decription_1=view.findViewById<TextView>(R.id.decription_ar)
        val image_user_1 =view.findViewById<ImageView>(R.id.image_user_ar)
        val image_1=view.findViewById<ImageView>(R.id.image_ar)


        title_1.text="$title"
        user_1.text="$user"
        date_1.text="$date"
        decription_1.text="$decription"

        Glide.with(this)
            .load(image_user)
            .into(image_user_1)

        Glide.with(this)
            .load(image)
            .into(image_1)



    }

}