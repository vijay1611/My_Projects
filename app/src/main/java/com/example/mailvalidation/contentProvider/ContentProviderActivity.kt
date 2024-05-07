package com.example.mailvalidation.contentProvider

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mailvalidation.R
import com.example.mailvalidation.databinding.ActivityContentProviderBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContentProviderActivity: AppCompatActivity() {

    lateinit var binding: ActivityContentProviderBinding
    lateinit var recyclerView: RecyclerView
     var adapter: ContactAdapter= ContactAdapter(this,listOf())
        val key1="key1"
    val key2 ="key2"
    var value1 = "value1"
    var value2 = "value2"
    //lateinit var adapter: ContactAdapter(Li)

    companion object {
        var CONTACTS_PERMISSION_REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)


      //  binding.btnSubmit.visibility = View.GONE
        //val contactsList: List<Contact> = Contact// Initialize your list of contacts
        val recyclerView: RecyclerView = findViewById(R.id.rvMain)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        permissionCheck()



        val sharedPreferences = applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val value1 = sharedPreferences.getString(key1, null)
        //val value2 = sharedPreferences.getString(key2, null)
       // return Pair(value1, value2)

        Log.e("shared***", value1.toString())
        val gson = Gson()
        val itemType = object : TypeToken<List<Contact>>() {}.type
        val data1:List<Contact> = if(value1!=null){
            gson.fromJson(value1, itemType)
        }else {
            listOf<Contact>()
        }

        if(value1==null){
            binding.btnSubmit.visibility = View.VISIBLE
        }else{
            binding.btnSubmit.visibility = View.GONE
        }



        adapter.setData(data1)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    adapter.setData(data1)
                } else {
                    // Filter contacts based on the newText
                    adapter.filterContacts(newText)
                }
                return true
            }
        })


    }



    fun permissionCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED  ) {
            // Permission is not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS,Manifest.permission.CALL_PHONE),
                CONTACTS_PERMISSION_REQUEST_CODE
            )
           // setadapter()
            getContacts()
            binding.btnSubmit.visibility = View.GONE

        } else {
            // Permission has already been granted
            // You can proceed with accessing contacts here
//       setadapter()
            binding.btnSubmit.visibility = View.GONE
            getContacts()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CONTACTS_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                // You can proceed with accessing contacts here
                getContacts()
                binding.btnSubmit.visibility = View.GONE
            } else {
                // Permission denied
                // Handle the case where the user denies permission
                Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("Range", "SuspiciousIndentation")
    fun getContacts() {
        val contacts = mutableListOf<Contact>()

        // Query the contacts content provider
        val contentResolver: ContentResolver = this.contentResolver
        var cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        CoroutineScope(Dispatchers.IO).launch {

            binding.progress.visibility = View.VISIBLE

            cursor?.use { cursor ->

                while (cursor.moveToNext()) {
                    val contactId =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val displayName =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    // Retrieve phone numbers for the contact
                    val phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(contactId),
                        null
                    )

                    val phoneNumbers = mutableListOf<Number>()
                    phoneCursor?.use { phoneCursor ->
                        while (phoneCursor.moveToNext()) {
                            val phoneNumber =
                                phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            phoneNumbers.add(Number(phoneNumber))
                        }

                    }
                    phoneCursor?.close()
                    if(displayName!=null && phoneNumbers!=null){
                        val contact = Contact(displayName, phoneNumbers)
                        contacts.add(contact)
                    }

                   // value1 = displayName
                   // value2 = phoneNumbers[0]



                }

            }
            val sharedPreferences =applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(key1, Gson().toJson(contacts))
            editor.apply()
            Log.e("cp*", Gson().toJson(contacts))
            cursor?.close()

        }.invokeOnCompletion {
            runOnUiThread{
                binding.progress.visibility = View.GONE
                adapter.setData(contacts)
            }

        }

    }
    fun saveDataToSharedPreferences(context: Context, key1: String, value1: String, key2: String, value2: String) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key1, value1)
        editor.putString(key2, value2)
        editor.apply()
    }

}