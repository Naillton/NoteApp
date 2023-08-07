package com.example.noteapp.fragments

import android.os.Bundle
import com.example.noteapp.room.model.Note
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.MainActivity
import com.example.noteapp.R
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.viewmodel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home) {
    // , SearchView.OnQueryTextListener

    // poderiamos usar o private lateinit no binding deixando o codigo menor e mais legivel
    // porem para evitar vazamentos utilizamos o _binding como nulo, usando recomendacoes do google
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel

        setUpRecyclerView()

        binding.floatingButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }

        binding.btnSearchHome.setOnClickListener {
            val query = binding.edtTextHome.text.toString()
            if (query.isEmpty() && query.isBlank()) {
                Toast.makeText(
                    context,
                    "Empty or Blank text",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                searchNote(query)
            }
        }
    }

    // funcao usada para aplicar binding no nosso layout
    // definindo o layout e usando o gerenciador para definir o tipo do layout
    // as suas colunas e a orientacao
    private fun setUpRecyclerView() {
        noteAdapter = NoteAdapter()
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity?.let {
            notesViewModel.getAllNotes().observe(
                viewLifecycleOwner
            ) { note: List<Note> ->
                noteAdapter.differ.submitList(note)
                updateUI(note)
            }
        }
    }

    // funcao para atualizar o estado da UI
    private fun updateUI(note: List<Note>?) {
        if (note != null) {
            if (note.isNotEmpty()) {
                binding.cardView.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            } else {
                binding.cardView.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
        }
    }

    // definindo funcao de pesquisa de nota, passando parametros que seram as querys de pesquisa
    private fun searchNote(query: String) {
        val searchQuery = "%$query"
        notesViewModel.searchNote(searchQuery).observe(
            viewLifecycleOwner
        ) { list: List<Note> ->
            noteAdapter.differ.submitList(list)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}