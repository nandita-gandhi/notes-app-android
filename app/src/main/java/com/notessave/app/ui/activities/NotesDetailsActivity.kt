package com.notessave.app.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.notessave.app.databinding.ActivityNotesDetailsBinding

class NotesDetailsActivity : AppCompatActivity() {

    companion object {
        const val BUNDLE_EXTRAS_NOTE = "BUNDLE_EXTRAS_NOTE"
    }

    private lateinit var binding: ActivityNotesDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteText = intent.getStringExtra(BUNDLE_EXTRAS_NOTE)
        binding.tvNoteText.text = noteText
    }
}