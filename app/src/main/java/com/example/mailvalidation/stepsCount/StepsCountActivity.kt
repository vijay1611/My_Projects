package com.example.mailvalidation.stepsCount

import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.getSystemService
import com.example.mailvalidation.R
import com.example.mailvalidation.databinding.ActivityStepsCountBinding
import com.example.mailvalidation.databinding.ActivityWelcomeBinding

class StepsCountActivity : AppCompatActivity() , SensorEventListener{

     lateinit  var mSensorManager : SensorManager
     lateinit var stepSensor : Sensor
     var totalSteps:Int =0
        var previewTotalSteps : Int =0
   lateinit var binding: ActivityStepsCountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStepsCountBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        stepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) as Sensor

        if(stepSensor == null) {
            Toast.makeText(this, "Sensor not working", Toast.LENGTH_SHORT).show()
        }else{
            Log.e("onResume","onResume called")
            mSensorManager.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_NORMAL)
        }

        resetSteps()
       // loadData()


    }

    override fun onResume() {
        super.onResume()

        binding.steps.text = "0"

    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }

    fun resetSteps(){
        binding.steps.setOnClickListener{
            Toast.makeText(this, "Long press to reset steps count", Toast.LENGTH_SHORT).show()
        }
        binding.steps.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                previewTotalSteps = totalSteps
                binding.steps.text = "0" // Setting text as a string "0"
                binding.progress.progress = 0
              //  saveData()
                return true
            }


        })
    }

    fun saveData(){
       var sharedPref : SharedPreferences = getSharedPreferences("myPref",Context.MODE_PRIVATE)
            var editor : SharedPreferences.Editor = sharedPref.edit()
        editor.putString("key1",previewTotalSteps.toString())
        editor.apply()
    }
    fun loadData(){
        var sharedPref : SharedPreferences = getSharedPreferences("myPref",Context.MODE_PRIVATE)
        var savedNumber = sharedPref.getFloat("key1",0f).toInt()
        previewTotalSteps = savedNumber
    }


var attempt=0
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            Log.e("onSensor","onSensor")
            if (it.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                totalSteps = event.values[0].toInt()
                Log.e("newTotal", totalSteps.toString())
                Log.e("prevSteps", previewTotalSteps.toString())
                val currentSteps = if (totalSteps >= previewTotalSteps) {
                    totalSteps - previewTotalSteps
                } else {
                    totalSteps
                }
                Log.e("currentSteps", currentSteps.toString())

                    binding.steps.text = currentSteps.toString()
                    binding.progress.progress = currentSteps


            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}