package com.example.noteapp.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.MainActivity
import com.example.noteapp.R
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.databinding.FragmentUpdateNoteBinding
import com.example.noteapp.room.model.Note
import com.example.noteapp.viewmodel.NoteViewModel


class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote: Note
    // usando o argumento passado no grafico de navegacao
    private val args: UpdateNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel
        // usando o args para passar a nota correta para ser atualizado pelo fragment
        currentNote = args.note!!

        binding.edtUpdateNoteTitle.setText(currentNote.title)
        binding.edtUpdateNoteBody.setText((currentNote.content))

        binding.floatingButtonUpdateNote.setOnClickListener {
            val title = binding.edtUpdateNoteTitle.text.toString().trim()
            val content = binding.edtUpdateNoteBody.text.toString().trim()

            // atencao ao passar os valores para note pois como estamos atualizando so precisamos
            // passar os valores pelo currentNote
            if (title.isNotEmpty() && content.isNotEmpty()) {
                val note = Note(currentNote.id, title, content)
                notesViewModel.updateNote(note)
                Toast.makeText(
                    view.context,
                    "Note Updated",
                    Toast.LENGTH_LONG
                ).show()
                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            } else {
                Toast.makeText(
                    context,
                    "Invalid Camps",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.floatingButtonDeleteNote.setOnClickListener {
            deleteNote()
        }
    }

    // criando alert dialog para deletar ou nao notas
    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("You want to delete this Note ?")
            setPositiveButton("Delete") {_,_ ->
                notesViewModel.deleteNote(currentNote)

                view?.findNavController()?.navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }

            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}