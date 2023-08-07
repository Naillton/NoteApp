package com.example.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.room.model.Note
import com.example.noteapp.room.repository.NoteRepository
import kotlinx.coroutines.launch

/**
 * criando viewmodel que fara a comunicacao do app com o banco de dados,
 * usando corrotinas para executar de forma assincrona nossas funcoes do repository,
 * as funcoes que nao sao suspensas getAllNotes e searchNotes nos nao usamos a coroutine
 */
class NoteViewModel(
    app: Application,
    private val noteRepository: NoteRepository
): AndroidViewModel(app) {

    fun insertNote(note: Note) {
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    fun getAllNotes() = noteRepository.getAllNotes()

    fun searchNote(query: String) = noteRepository.searchNotes(query)
}