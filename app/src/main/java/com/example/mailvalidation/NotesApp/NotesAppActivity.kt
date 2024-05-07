package com.example.mailvalidation.NotesApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
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

class NotesAppActivity : AppCompatActivity(), NoteAdapter.TodoClickListener {

    lateinit var adapter: NoteAdapter
    lateinit var database: NoteDatabase

    var list = listOf<Note>()

    lateinit var tit : EditText
    lateinit var des : EditText
    lateinit var btn_save : Button
    lateinit var rv_main : RecyclerView
    lateinit var progress : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_app2)



        tit = findViewById(R.id.title)
        des = findViewById(R.id.desc)
        btn_save = findViewById(R.id.save_btn)
        progress = findViewById(R.id.progress)
        database = NoteDatabase.getInstance(this)
        adapter = NoteAdapter(this,ArrayList(list), this)
        rv_main = findViewById(R.id.rv_main)
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = adapter
        adapter.notifyDataSetChanged()



        btn_save.setOnClickListener {
            progress.visibility = View.VISIBLE

            lifecycleScope.launch(Dispatchers.IO) {
                var title_data = tit.text.toString()
                var desc_data = des.text.toString()
                var note = Note(title=title_data, desc = desc_data)
                database.noteDao().addNote(note)
                Log.e("vij*", note.toString() )
            }.invokeOnCompletion {
                runOnUiThread{
                    tit.setText("")
                    des.setText("")
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


}
