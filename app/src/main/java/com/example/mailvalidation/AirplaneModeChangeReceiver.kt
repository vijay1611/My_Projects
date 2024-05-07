package com.example.mailvalidation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirplaneModeChangeReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplanMethodIsEnabled = intent?.getBooleanExtra("state",false) ?: return

        if(isAirplanMethodIsEnabled){
            Toast.makeText(context,"Airplane mode Enabled",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context,"Airplane mode Disabled",Toast.LENGTH_LONG).show()
        }
    }

}