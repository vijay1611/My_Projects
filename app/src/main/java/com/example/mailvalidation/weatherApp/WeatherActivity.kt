package com.example.mailvalidation.weatherApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mailvalidation.R
import com.example.mailvalidation.databinding.ActivityWeatherBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.util.Date
import javax.security.auth.callback.Callback
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

8

        binding.mainContainer.visibility = View.GONE

        binding.submitBtn.setOnClickListener {
            var city =binding.city.text.trim().toString()
            binding.progress.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.Default).launch {

                val callObj = WeatherRetro.getRetrofitApi()?.getWeather(city)
                callObj?.enqueue(object : retrofit2.Callback<WeatherModel> {
                    override fun onResponse(
                        call: Call<WeatherModel>,
                        response: Response<WeatherModel>
                    ) {
                        binding.progress.visibility = View.GONE
                        binding.error.visibility = View.GONE
                        binding.mainContainer.visibility = View.VISIBLE

                        if (response.isSuccessful) {
                            val res= response.body()
                            Log.e("***", res.toString())
                            res?.let {
                                binding.humidity.text = it.wind
                                binding.temp.text=it.temperature
                                binding.location.text = city
                                binding.maxTemp.text=it.forecast[0].day.toString()
                                binding.minTemp.text = it.description
                                binding.pressure.text=it.forecast[0].day.toString()
                               // binding.updatedAt.text = Duration().
                            }
                        }else{
                            Log.e("***", response.message())
                        }
                    }

                    override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                        binding.mainContainer.visibility = View.GONE
                        binding.error.visibility = View.VISIBLE
                        binding.progress.visibility = View.GONE
                        Log.e("***f",t.message.toString())
                    }


                })

            }
        }


    }
}