package com.vedant.notes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.vedant.notes.AppDatabase
import com.vedant.notes.adapters.NotesAdapter
import com.vedant.notes.databinding.ActivityMainBinding
import com.vedant.notes.models.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val DB_NAME = "Notes.db"

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var listNotes = arrayListOf<Notes>()
    var notesAdapter = NotesAdapter(listNotes)

    private val db by lazy {
        AppDatabase.getDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.btnAddNote.setOnClickListener {
            val intent = Intent(this, CreateNewNoteActivity::class.java)
            startActivity(intent)
        }

        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.notesAdapter
        }

        GlobalScope.launch(Dispatchers.Main) {
            listNotes.clear()
            listNotes.addAll(db.notesDao().getAllNotes())
            notesAdapter.notifyDataSetChanged()
            Log.d("RECYCLER_VIEW_UPDATE", "updated list size = ${listNotes.size}")
        }


    }
}