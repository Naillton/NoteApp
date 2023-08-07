package com.example.noteapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.noteapp.MainActivity
import com.example.noteapp.R
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.databinding.FragmentNewNoteBinding
import com.example.noteapp.room.model.Note
import com.example.noteapp.viewmodel.NoteViewModel


class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var noteView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel
        noteView = view

        binding.floatingButtonSave.setOnClickListener {
            saveNote(noteView)
        }

    }

    @SuppressLint("ResourceType")
    private fun saveNote(view: View) {
        val noteTitle = binding.edtAddNoteTitle.text.toString().trim()
        val noteBody = binding.edtAddNoteBody.text.toString().trim()

        if (noteTitle.isNotEmpty() && noteBody.isNotEmpty()) {
            val note = Note(0, noteTitle, noteBody)

            notesViewModel.insertNote(note)

            Toast.makeText(
                noteView.context,
                "Note Saved",
                Toast.LENGTH_LONG
            ).show()
            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
        } else {
            Toast.makeText(
                noteView.context,
                "Invalid Camps",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}