package com.vedant.notes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.vedant.notes.AppDatabase
import com.vedant.notes.R
import com.vedant.notes.databinding.ActivityCreateNewNoteBinding
import com.vedant.notes.models.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateNewNoteActivity : AppCompatActivity() {

    lateinit var binding:ActivityCreateNewNoteBinding

    private val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "Notes.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewNoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnFormCompleted.setOnClickListener {
            when {
                binding.notesTitleTextView.text.toString().isEmpty() -> {
                    Toast.makeText(this, "Please fill Title", Toast.LENGTH_SHORT).show()
                }
                binding.notesContentTextView.text.toString().isEmpty() -> {
                    Toast.makeText(this, "Please fill some text", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val title = binding.notesTitleTextView.text.toString()
                    val content = binding.notesContentTextView.text.toString()
                    val color = 0L
                    GlobalScope.launch(Dispatchers.IO) {
                        db.notesDao().insert(Notes(title = title, content = content, color = color))
                        val intent = Intent(this@CreateNewNoteActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}