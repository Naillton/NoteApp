package com.example.noteapp.room.repository

import com.example.noteapp.room.database.NoteDatabase
import com.example.noteapp.room.model.Note

class NoteRepository(private val db: NoteDatabase) {

    suspend fun insertNote(note: Note) {
        db.getNoteDao().insertNote(note)
    }

    suspend fun updateNote(note: Note) {
        db.getNoteDao().updateNote(note)
    }

    suspend fun  deleteNote(note: Note) {
        db.getNoteDao().deleteNote(note)
    }

    fun getAllNotes() = db.getNoteDao().getAllNotes()

    fun searchNotes(query: String?) = db.getNoteDao().searchNotes(query)
}