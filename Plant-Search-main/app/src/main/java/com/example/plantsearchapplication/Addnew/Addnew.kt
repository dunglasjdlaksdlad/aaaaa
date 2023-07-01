package com.example.plantsearchapplication.Addnew

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.plantsearchapplication.R
import android.Manifest
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.plantsearchapplication.Models.SP
import com.example.plantsearchapplication.databinding.ActivityAddnewBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream


class Addnew : AppCompatActivity() {
    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    }
    lateinit var imageView: ImageView
    var sImage:String? =""
    private lateinit var db: DatabaseReference
    private lateinit var binding: ActivityAddnewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnew)

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_TAKE_PHOTO)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_SELECT_IMAGE_IN_ALBUM)

        imageView = findViewById(R.id.image)
        val take: Button = findViewById(R.id.take)
        take.setOnClickListener {
            takePhoto()
        }
        val pick: Button = findViewById(R.id.pick)
        pick.setOnClickListener {
            selectImageInAlbum()
        }
        val insert: Button = findViewById(R.id.add)
        insert.setOnClickListener {
            insertdata()
        }
    }

    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }
    fun takePhoto() {
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent1.resolveActivity(packageManager) != null) {
            startActivityForResult(intent1, REQUEST_TAKE_PHOTO)
        }
    }

    fun insertdata(){
        val name = findViewById<EditText?>(R.id.name).text.toString()
        val sp = findViewById<EditText?>(R.id.name).text.toString()
        val kingdom = findViewById<EditText?>(R.id.name).text.toString()
        val family=findViewById<EditText?>(R.id.name).text.toString()
        val decription=findViewById<EditText?>(R.id.name).text.toString()
        db= FirebaseDatabase.getInstance().getReference("Species")
        val item= SP(image=sImage,name=name,kingdom=kingdom,family=family,decription=decription)
        db.child(sp).child("sp").setValue(sp)
        db.child(sp).child(sp).child(name).setValue(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO) {
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)
        }
        if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM) {
            imageView.setImageURI(data?.data)
        }
        sImage=data?.dataString
    }

}