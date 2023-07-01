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
    lateinit var imageView: ImageView
    private val cameraRequest = 1888
    var sImage:String? =""
    private lateinit var db: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnew)

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)
        imageView = findViewById(R.id.image)
    }

    fun pick(view: View) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            ActivityResultLauncher.launch(intent)
        }
    }
    fun take(view: View) {
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent1.resolveActivity(packageManager) != null) {
            ActivityResultLauncher.launch(intent1)
        }
    }

    fun Add(view: View){
        val name = findViewById<EditText?>(R.id.name).text.toString()
        val sp = findViewById<EditText?>(R.id.name).text.toString()
        val kingdom = findViewById<EditText?>(R.id.name).text.toString()
        val family=findViewById<EditText?>(R.id.name).text.toString()
        val decription=findViewById<EditText?>(R.id.name).text.toString()
        //sImage.toString()
        db= FirebaseDatabase.getInstance().getReference("Species")
        val item= SP(image=sImage,name=name,kingdom=kingdom,family=family,decription=decription)

        db.child(sp).child("sp").setValue(sp)
        db.child(sp).child(sp).child(name).setValue(item)
    }

    private val ActivityResultLauncher =registerForActivityResult<Intent,ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ){result: ActivityResult ->
        if(result.resultCode== RESULT_OK){
            val uri=result.data!!.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val myBitmap= BitmapFactory.decodeStream(inputStream)
                val stream =ByteArrayOutputStream()
                myBitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
                val bytes=stream.toByteArray()
                sImage= Base64.encodeToString(bytes,Base64.DEFAULT)
                imageView.setImageBitmap(myBitmap)
                inputStream!!.close()
            }catch(ex:Exception){}
        }
    }
}