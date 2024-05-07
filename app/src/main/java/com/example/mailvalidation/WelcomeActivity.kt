package com.example.mailvalidation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mailvalidation.NotesApp.NotesAppActivity
import com.example.mailvalidation.Retrofit2.RetrofitMainActivity
import com.example.mailvalidation.databinding.ActivityWelcomeBinding
import com.example.mailvalidation.image.ImagePickerActivity
import com.example.mailvalidation.retrofit.RetrofitPracticeActivity
import com.example.mailvalidation.snakeGame.SnakeGameActivity
import com.example.mailvalidation.stepsCount.StepsCountActivity
import com.example.mailvalidation.weatherApp.WeatherActivity

class WelcomeActivity : AppCompatActivity() {

    lateinit var binding : ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var num1= "1.3.5"
        var num2 ="1.3.6"

       var num11= num1.split(".")
        Log.d("num11*", num11.toString())
       var num22= num2.split(".")

        for( i in num11.indices){
          if(num11[i] ==num22[i]){
              continue
          } else if(num11[i].toInt()>=num22[i].toInt()){
              println("$num1 is greater than $num2")
              break
          }else{
              println("$num2 is greater than $num1")
          }
        }


        binding.stepsCountApp.setOnClickListener {
        startActivity(Intent(this,StepsCountActivity::class.java))
        }
        binding.imagePicker.setOnClickListener {
            startActivity(Intent(this,ImagePickerActivity::class.java))
        }
        binding.weather.setOnClickListener {
            startActivity(Intent(this,WeatherActivity::class.java))
        }
        binding.retrofit2.setOnClickListener {
            startActivity(Intent(this, RetrofitMainActivity::class.java))
        }
        binding.retrofit.setOnClickListener {
            startActivity(Intent(this, RetrofitPracticeActivity::class.java))
        }
        binding.snakeGame.setOnClickListener {
            startActivity(Intent(this, SnakeGameActivity::class.java))
        }
        binding.notesApp.setOnClickListener {
            startActivity(Intent(this, NotesAppActivity::class.java))
        }
    }
}