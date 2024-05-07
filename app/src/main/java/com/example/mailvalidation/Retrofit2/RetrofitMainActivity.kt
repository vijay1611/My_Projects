package com.example.mailvalidation.Retrofit2

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mailvalidation.R
import com.example.mailvalidation.databinding.ActivityRetrofitMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class RetrofitMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityRetrofitMainBinding
    private val CHANNEL_ID = "your_channel_id"
    private val NOTIFICATION_ID = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofitMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val quotesApi = RetrofitHelper.getInstance().create(RetroInterface::class.java)
        GlobalScope.launch {
            val result = quotesApi.getQuotes()
                val quotes = result.body()?.results
                if (!quotes.isNullOrEmpty()) {
                   for(quote in quotes) {
                       withContext(Dispatchers.Main) {
                           binding.text1.text = quote.content
                       }
                   }
                } else {

                }
            }
        createNotificationChannel()
        showNotification()
        }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Your Channel Name"
            val descriptionText = "Your Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Your Notification Title")
            .setContentText("Your Notification Content")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }
    }

