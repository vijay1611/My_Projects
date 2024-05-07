package com.example.mailvalidation.Notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ContentInfoCompat.Flags
import com.example.mailvalidation.R
import com.example.mailvalidation.databinding.ActivityNotificationViewBinding
import com.example.mailvalidation.databinding.ActivityPendingIntentBinding
import com.google.android.material.timepicker.TimeFormat
import java.sql.Time
import java.text.DateFormat
import java.util.Calendar
import java.util.Date

class PendingIntentActivity : AppCompatActivity() {

    lateinit var binding: ActivityPendingIntentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        binding.btnSUbmit.setOnClickListener{
            sheduleNotification()
        }

    }

    private fun sheduleNotification() {

        val intent = Intent(applicationContext,Notification::class.java)
        val title = binding.title.text.toString()
        val desc = binding.desc.text.toString()

        intent.putExtra(titleExtra,title)
        intent.putExtra(messageextra,desc)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(title,desc,time)

    }

    private fun showAlert( title: String, desc: String,time: Long) {
        val date = Date(time)

        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Title: "+title)
            .setMessage("\nMessage: "+desc +
            "\nAt: "+dateFormat.format(date)+" "+timeFormat.format(date))
            .setPositiveButton("Okay"){_,_->}
            .show()

            }

    private fun getTime(): Long {
        val minute = binding.time.minute
        val hour = binding.time.hour
        val date = binding.date.dayOfMonth
        val month = binding.date.month
        val year = binding.date.year

        val calendar = Calendar.getInstance()
        calendar.set(year,month,date,hour,minute)
        return calendar.timeInMillis

    }

    @SuppressLint("NewApi")
    private fun createNotificationChannel() {
        val name = "Notif channel"
        val desc = " A Description of the channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId,name,importance)
        channel.description= desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)


    }
}