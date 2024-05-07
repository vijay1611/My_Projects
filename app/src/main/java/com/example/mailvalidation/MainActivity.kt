package com.example.mailvalidation

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mailvalidation.database.CartoonDatabase
import com.example.mailvalidation.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var receiver: AirplaneModeChangeReceiver
    lateinit var  mail : EditText
    //lateinit var  btnSubmit : Button
    @SuppressLint("MissingInflatedId")
    lateinit var binding : ActivityMainBinding
    var adapter : CartoonAdapter=CartoonAdapter(mutableListOf())
  //  lateinit var data:CartoonItem
   lateinit var  db :CartoonDatabase
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = CartoonDatabase.getInstance(application)
        receiver = AirplaneModeChangeReceiver()
        val vm = ViewModelProvider(this)[EmailViewModel::class.java]
         vm.getData()
        vm.data.observe(this) { it ->
            Log.e("****",it.toString())
                vm.saveData(db)
        }
        vm.data1.observe(this) { it ->
            Log.e("****",it.toString())
            if (it.isEmpty()){
                vm.getDataFromRoom(db)
            }else{
                adapter.setvalue(it)
            }

        }

      binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter

        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver,it)
        }
        binding.buttonNotify.setOnClickListener{
         val inputText = "Some extra data"
         val serviceIntent = Intent(this, MyForegroundService::class.java).apply {
            putExtra("inputExtra", inputText)
    }
         startService(serviceIntent)
    }

//        btnSubmit.setOnClickListener {
//    if (vm.validateMail(mail.text.toString())) {
//        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
//    }
//}
//        binding.buttonNotify.setOnClickListener {
//                //addNotification()
//            Log.d("TAG", "onCreate: button click")
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.POST_NOTIFICATIONS
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                Log.d("TAG", "onCreate: button click")
//                addNotification()
//                //addNotifi().notify((System.currentTimeMillis()/10000).toInt(),notificationBuild().build())
//            }else{
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                    requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS),1)
//                }
//            }
//            }
        }

    private fun addNotifi(): NotificationManagerCompat {
        val notificationManager=NotificationManagerCompat.from(this)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel = NotificationChannel("main id","Cartoon",NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        return notificationManager
    }

    private fun notificationBuild(): NotificationCompat.Builder {
        return NotificationCompat.Builder(this,"channel")
            .setContentTitle("Notifications Example")
            .setContentText("This is a notification message")
            .setSmallIcon(R.drawable.sym_def_app_icon)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }




    private fun addNotification() {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.sym_def_app_icon) //set icon for notification
            .setContentTitle("Notifications Example") //set title of notification
            .setContentText("This is a notification message") //this is notification message
            .setAutoCancel(true) // makes auto cancel of notification
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //set priority of notification
        val notificationIntent = Intent(this, NotificationView::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //notification message will get at NotificationView
        notificationIntent.putExtra("message", "This is a notification message")
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(pendingIntent)

        // Add as notification
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder.build())
    }


}