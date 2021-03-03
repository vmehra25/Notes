package com.vedant.notes.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vedant.notes.R
import com.vedant.notes.models.Notes
import com.vedant.notes.ui.EditNotesActivity

const val TITLE = "TITLE"
const val CONTENT = "CONTENT"
const val ID = "ID"

class NotesAdapter(private val list: ArrayList<Notes>): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    inner class NotesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(note: Notes){
            itemView.findViewById<TextView>(R.id.notesTitleTextView).text = note.title
            itemView.findViewById<TextView>(R.id.notesContentTextView).text = note.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notes_list_item, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.findViewById<com.google.android.material.card.MaterialCardView>(R.id.listItem).setOnClickListener {
            val intent = Intent(holder.itemView.context, EditNotesActivity::class.java)
            intent.putExtra(TITLE, list[position].title)
            intent.putExtra(CONTENT, list[position].content)
            intent.putExtra(ID, list[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return list[position].id
    }

}