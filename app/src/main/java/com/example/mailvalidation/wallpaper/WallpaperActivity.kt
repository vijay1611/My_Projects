package com.example.mailvalidation.wallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mailvalidation.R
import com.example.mailvalidation.databinding.ActivityImplicitIntentBinding
import com.example.mailvalidation.databinding.ActivityWallpaperBinding

class WallpaperActivity : AppCompatActivity() {
    lateinit var binding: ActivityWallpaperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)






    }
}