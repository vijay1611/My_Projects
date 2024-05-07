package com.example.mailvalidation.Notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.mailvalidation.R

const val notificationId =1
const val channelId="channel1"
const val titleExtra = "titleExtra"
const val messageextra = " messageExtra"


class Notification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        val notification  = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(intent?.getStringExtra(titleExtra))
            .setContentText(intent?.getStringExtra(messageextra))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId,notification)




    }
}