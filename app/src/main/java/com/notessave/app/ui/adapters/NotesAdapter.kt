package com.notessave.app.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.notessave.app.database.Note
import com.notessave.app.databinding.RowSingleNoteRvBinding

class NotesAdapter(private val context: Context, private val onClickAction: OnDeleteClickListener) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val notesList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(RowSingleNoteRvBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notesList[position])
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun updateNoteList(list: List<Note>) {
        notesList.clear()
        notesList.addAll(list)
        notifyDataSetChanged()
    }

    inner class NotesViewHolder(private val binding: RowSingleNoteRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.apply {
                tvNote.text = note.text

                binding.root.setOnClickListener {
                    onClickAction.onNoteClick(note.text)
                }

                ivDelete.setOnClickListener {
                    onClickAction.onDeleteClick(note)
                }
            }
        }

    }

    interface OnDeleteClickListener {
        fun onDeleteClick(note: Note)
        fun onNoteClick(noteText: String)
    }
}

