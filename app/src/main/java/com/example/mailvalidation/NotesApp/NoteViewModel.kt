package com.example.mailvalidation.NotesApp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roughnote.Note

class NoteViewModel:ViewModel() {

    private var _date = MutableLiveData<List<Note>>()
    private val data:LiveData<List<Note>> =_date




}