package com.example.mailvalidation.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mailvalidation.R
import com.example.mailvalidation.databinding.ActivityRetrofitPracticeBinding
import com.example.mailvalidation.weatherApp.WeatherModel
import com.example.mailvalidation.weatherApp.WeatherRetro
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import kotlin.concurrent.thread

class RetrofitPracticeActivity : AppCompatActivity() {

    lateinit var binding : ActivityRetrofitPracticeBinding

    var list:ArrayList<DataX> = ArrayList()
    var adapter = UsersAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofitPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  adapter = UsersAdapter(list)
        binding.rvRetrofit.layoutManager = LinearLayoutManager(this)



            val callObj = DataSource.getRetrofitApi()?.getUsers()
            callObj?.enqueue(object : retrofit2.Callback<Data> {
                override fun onResponse(
                    call: Call<Data>,
                    response: Response<Data>
                ) {
                    if (response.isSuccessful) {
                        val res= response.body()
                        Log.e("***if", res.toString())
                        res?.let {
                            for(ress in res.data){
                            list.add(DataX(ress.id,ress.first_name,ress.last_name,ress.email))
                            Log.e("**1234", list.toString() )
                            }


                            // binding.updatedAt.text = Duration().
                        }
                    }else{
                        Log.e("***else", response.message())
                    }
                }

                override fun onFailure(call: Call<Data>, t: Throwable) {
                    Log.e("***f",t.message.toString())
                }


            })
        adapter.setvalue(list)
        //adapter = UsersAdapter(list)
        binding.rvRetrofit.adapter = adapter
        Log.e("**123", list.toString() )

        }




    }





