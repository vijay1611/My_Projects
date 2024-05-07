package com.example.mailvalidation.image

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import com.example.mailvalidation.databinding.ActivityImagePickerBinding
import java.io.IOException

class ImagePickerActivity : AppCompatActivity() {
    lateinit var binding : ActivityImagePickerBinding
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetImage.setOnClickListener {
            getImage()
        }
       val REQUEST_CODE_READ_EXTERNAL_STORAGE = 101

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_READ_EXTERNAL_STORAGE)
        } else {
            // Permission is granted, proceed to access the URI
            //handleUri(uri)
        }




    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val REQUEST_CODE_READ_EXTERNAL_STORAGE =102
        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted, handle the URI
                  //  handleUri(uri)
                } else {
                    // Permission denied, show a message or handle it accordingly
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.P)
    private fun handleUri(uri: Uri) {
        try {
            val drawable = ImageDecoder.createSource(contentResolver, uri)
            val bitmap = ImageDecoder.decodeDrawable(drawable).toBitmap()
            binding.image1.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getImage() {
        val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
        startActivityForResult(intent,101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
            if(requestCode==101){
                val uri = data?.data

                binding.image1.setImageURI(uri)
                saveImagePathToSharedPreferences( uri.toString())
                binding.image1.setImageURI(getPath()?.toUri())
            }
        }
    }
    fun saveImagePathToSharedPreferences( imagePath: String) {
        val sharedPreferences =getSharedPreferences("image_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("image_path", imagePath)
        editor.apply()
    }
    fun getPath(): String? {
        val sharedPreferences =getSharedPreferences("image_preferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("image_path", null)
    }
}