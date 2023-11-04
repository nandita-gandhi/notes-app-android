package com.notessave.app.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notessave.app.database.Note
import com.notessave.app.database.NoteDatabase
import com.notessave.app.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    val notesList: LiveData<List<Note>>
    private val noteRepository: NoteRepository

    init {
        val dao = NoteDatabase.getDatabase(application.applicationContext).noteDao()
        noteRepository = NoteRepository(dao)
        notesList = noteRepository.allNotes
    }

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteNote(note)
    }
}