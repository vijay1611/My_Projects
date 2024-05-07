package com.example.roughnote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mailvalidation.R

class NoteAdapter(private var context: Context, var list: ArrayList<Note>, val listener: TodoClickListener):RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val database = NoteDatabase.getInstance(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.notes_list_item,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.desc.text = list[position].desc
        holder.deleteIcon.setOnClickListener{
            listener.onItemClicked(list[position],position)
        }
    }

    inner class NoteViewHolder(private var itemView: View):RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.title_text)
        var desc = itemView.findViewById<TextView>(R.id.desc_text)
        var deleteIcon = itemView.findViewById<ImageView>(R.id.delete_image)
    }

    fun getData(list: ArrayList<Note>){
        this.list = list
        notifyDataSetChanged()
    }

    fun deleteRecord(position: Int) {
       list.removeAt(position)
        notifyDataSetChanged()
    }

    interface TodoClickListener {
        fun onItemClicked(todo: Note,position: Int)
    }
}



