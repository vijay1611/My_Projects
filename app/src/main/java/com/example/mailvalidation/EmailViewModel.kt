package com.example.mailvalidation

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mailvalidation.database.CartoonDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EmailViewModel:ViewModel() {

   private var _data = MutableLiveData<MutableList<CartoonItem>>()
    var  data : LiveData<MutableList<CartoonItem>> = _data
    private var _data1 = MutableLiveData<MutableList<CartoonItem>>()
    var  data1 : LiveData<MutableList<CartoonItem>> = _data1
    fun validateMail(mail:String)= Patterns.EMAIL_ADDRESS.matcher(mail).matches()

    fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            val base_url = "https://api.sampleapis.com/cartoons/"

            val api_service: CartoonInterface by lazy {
                var retrofit = Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

                retrofit.create(CartoonInterface::class.java)

            }
            api_service.getData().enqueue(object : Callback<List<CartoonItem>> {
                override fun onResponse(
                    call: Call<List<CartoonItem>>,
                    response: Response<List<CartoonItem>>
                ) {
                    Log.e("****","(((")
                    _data.value = response.body()?.toMutableList()
                    Log.e("****",response.body()?.toMutableList().toString())
                }

                override fun onFailure(call: Call<List<CartoonItem>>, t: Throwable) {
                    Log.e("****",t.message.toString())
                }
            })
        }
    }
    fun saveData(db: CartoonDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            _data.value?.toList()?.let { db.cartoonDao().insertUser(it) }
            _data1.postValue(mutableListOf())


        }
    }
    fun getDataFromRoom(db: CartoonDatabase){
        CoroutineScope(Dispatchers.IO).launch {
            val item=db.cartoonDao().getAllCartoonUsers().toMutableList()
            _data1.postValue(item)

        }

    }

}