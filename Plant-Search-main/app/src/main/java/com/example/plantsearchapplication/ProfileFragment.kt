package com.example.plantsearchapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var circleImageView: CircleImageView
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val changeProfileButton = view.findViewById<Button>(R.id.changeProfileButton)
        changeProfileButton.setOnClickListener {
            showChangeProfileDialog()
        }

        val textViewName = view.findViewById<TextView>(R.id.textViewName)
        val savedName = sharedPreferences.getString("name", "")
        textViewName.text = savedName

        val tvEmail = view.findViewById<TextView>(R.id.tv_email)

        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val email: String? = currentUser?.email
        tvEmail.text = email

        circleImageView = view.findViewById(R.id.imageView)
        circleImageView.setOnClickListener {
            selectImageFromGallery()
        }

        val selectedImageUriString = sharedPreferences.getString("selectedImageUri", "")
        if (!selectedImageUriString.isNullOrEmpty()) {
            selectedImageUri = Uri.parse(selectedImageUriString)
            loadImageWithGlide(selectedImageUri!!)
        }

        return view
    }

    private fun showChangeProfileDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_profile, null)

        val editTextName = view.findViewById<EditText>(R.id.editTextName)
        builder.setView(view)
        val dialog = builder.create()
        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            updateProfile(editTextName.text.toString(), dialog)
        }
        dialog.show()
    }

    private fun updateProfile(newName: String, dialog: AlertDialog) {
        if (newName.isEmpty()) {
            Toast.makeText(requireContext(), "Name is empty!!!", Toast.LENGTH_SHORT).show()
            return
        }

        val textViewName = view?.findViewById<TextView>(R.id.textViewName)
        textViewName?.text = newName

        val editor = sharedPreferences.edit()
        editor.putString("name", newName)
        editor.apply()

        dialog.dismiss()
        Toast.makeText(requireContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show()
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            if (selectedImageUri != null) {
                loadImageWithGlide(selectedImageUri!!)
                saveImageUri(selectedImageUri!!)
            }
        }
    }

    private fun loadImageWithGlide(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
            .into(circleImageView)
    }

    private fun saveImageUri(uri: Uri) {
        val editor = sharedPreferences.edit()
        editor.putString("selectedImageUri", uri.toString())
        editor.apply()
    }

    companion object {
        private const val REQUEST_IMAGE_GALLERY = 1001
    }
}


