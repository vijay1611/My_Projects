package com.example.mailvalidation.NotesApp

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mailvalidation.R
import com.example.roughnote.Note
import com.example.roughnote.NoteAdapter
import com.example.roughnote.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Path
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import kotlin.math.log

class NotesAppActivity : AppCompatActivity(), NoteAdapter.TodoClickListener {

    lateinit var adapter: NoteAdapter
    lateinit var database: NoteDatabase

    var list = listOf<Note>()

    lateinit var tit : EditText
    lateinit var des : EditText
    lateinit var btn_save : Button
    lateinit var rv_main : RecyclerView
    lateinit var progress : ProgressBar
    lateinit var selectImage : ImageView
    lateinit var imageClickText : TextView
         var path : String =""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_app2)



        tit = findViewById(R.id.title)
        des = findViewById(R.id.desc)
        imageClickText = findViewById(R.id.cameraText)
        selectImage =findViewById(R.id.getImage)
        btn_save = findViewById(R.id.save_btn)
        progress = findViewById(R.id.progress)
        database = NoteDatabase.getInstance(this)
        adapter = NoteAdapter(this,ArrayList(list), this)
        rv_main = findViewById(R.id.rv_main)
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = adapter
        adapter.notifyDataSetChanged()


       selectImage.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
            startActivityForResult(intent,101)
        }

        btn_save.setOnClickListener {
            progress.visibility = View.VISIBLE
            if(tit.text.isEmpty() || des.text.isEmpty() || path.isEmpty()){
                Toast.makeText(this, "fill all", Toast.LENGTH_SHORT).show()
                progress.visibility = View.GONE
            }else{
                lifecycleScope.launch(Dispatchers.IO) {
                var title_data = tit.text.toString()
                var desc_data = des.text.toString()
                var date1 =Date.from(Instant.now())
                //  var date2 = SimpleDateFormat("DD_MM_YYYY hh:mm", Locale.ENGLISH).format(date1)
                var note = Note(title=title_data, desc = desc_data,image=path, date =date1 )
                database.noteDao().addNote(note)
                path=""
                Log.e("vij*", note.toString() )
                }.invokeOnCompletion {
                    runOnUiThread{
                        tit.setText("")
                        des.setText("")
                        selectImage.setImageResource(R.drawable.back_corner)
                        imageClickText.visibility = View.VISIBLE
                        progress.visibility= View.GONE
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                        adapter.getData(ArrayList(list))
                        lifecycleScope.launch(Dispatchers.IO) {
                            list=   database.noteDao().getNote()
                            Log.e("vij1*", list.toString() )
                        }.invokeOnCompletion {
                            runOnUiThread {
                                adapter = NoteAdapter(this,ArrayList(list),this)
                                rv_main.adapter = adapter
                            }
                        }
                    }   }

            }
            }







        lifecycleScope.launch(Dispatchers.IO) {
            list=   database.noteDao().getNote()
            Log.e("vij1*", list.toString() )
        }.invokeOnCompletion {
            runOnUiThread {
                adapter = NoteAdapter(this, ArrayList(list),this)
                rv_main.adapter = adapter
            }
        }





        }

    override fun onItemClicked(todo: Note,position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            database.noteDao().deleteNote(todo)



        }.invokeOnCompletion {
            runOnUiThread {
                adapter.deleteRecord(position)

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
            if(requestCode==101){
                var uri = data?.data.toString()
                path = uri

                selectImage.setImageURI(Uri.parse(path))
                Toast.makeText(this, path.toString(), Toast.LENGTH_SHORT).show()
                imageClickText.visibility = View.GONE

            }
        }
    }


}
