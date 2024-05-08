package com.example.mailvalidation.ImplicitIntent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.example.mailvalidation.R
import com.example.mailvalidation.databinding.ActivityImplicitIntentBinding

class ImplicitIntentActivity : AppCompatActivity() {
    lateinit var binding : ActivityImplicitIntentBinding
    var isCameraPicked=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImplicitIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
            startActivityForResult(intent,101)
        }

        binding.btnSubmit.setOnClickListener {
            var text = binding.edtText.text.toString()
            var intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,text)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent,"Share to: "))
        }
        if(binding.edtText.text.isNotEmpty() && isCameraPicked==true){
            binding.btnSubmit.visibility= View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
            if (requestCode==101){
                val uri = data?.data
                binding.imageView.setImageURI(uri)
                isCameraPicked=true
                binding.cameraText.visibility = View.GONE

            }
        }

    }
}