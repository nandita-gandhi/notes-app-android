package com.notessave.app.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.notessave.app.database.Note
import com.notessave.app.databinding.ActivityMainBinding
import com.notessave.app.ui.adapters.NotesAdapter
import com.notessave.app.viewModels.NotesViewModel

class MainActivity : AppCompatActivity(), NotesAdapter.OnDeleteClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NotesViewModel
    private lateinit var mAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup recyclerView
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        mAdapter = NotesAdapter(this, this)
        binding.rvNotes.adapter = mAdapter

        // Observe Data
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NotesViewModel::class.java]

        viewModel.notesList.observe(this) { list ->
            list?.let { notes ->
                mAdapter.updateNoteList(notes)
            }
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {
            btnSubmit.setOnClickListener {
                if (etNote.text.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Enter something to add", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                } else {
                    viewModel.insertNote(Note(etNote.text.toString()))
                    etNote.text.clear()
                    hideKeyboard()

                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etNote.windowToken, 0)
    }

    override fun onDeleteClick(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "Successfully deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onNoteClick(noteText: String) {
        hideKeyboard()
        val intent = Intent(this, NotesDetailsActivity::class.java)
        intent.putExtra(NotesDetailsActivity.BUNDLE_EXTRAS_NOTE, noteText)
        startActivity(intent)
    }
}