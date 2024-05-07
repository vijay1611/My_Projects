package com.example.mailvalidation

import android.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mailvalidation.databinding.ActivityMainBinding
import com.example.mailvalidation.databinding.ActivityNotificationViewBinding


class NotificationView : AppCompatActivity() {
    lateinit var binding : ActivityNotificationViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //getting the notification message
        //getting the notification message
        val message = intent.getStringExtra("message")
        binding.textView.setText(message)

    }
}