package com.vedant.notes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.vedant.notes.AppDatabase
import com.vedant.notes.R
import com.vedant.notes.adapters.CONTENT
import com.vedant.notes.adapters.ID
import com.vedant.notes.adapters.TITLE
import com.vedant.notes.databinding.ActivityEditNotesBinding
import com.vedant.notes.models.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditNotesActivity : AppCompatActivity() {

    private val db by lazy {
        AppDatabase.getDatabase(this)
    }

    lateinit var title:String
    lateinit var content:String
    lateinit var binding:ActivityEditNotesBinding
    var id:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = intent.extras?.getString(TITLE)!!.toString()
        content = intent.extras?.getString(CONTENT)!!.toString()
        id = intent.extras?.getLong(ID)!!

        binding.notesTitleTextView.setText(title)
        binding.notesContentTextView.setText(content)

        binding.btnFormCompleted.setOnClickListener {
            when{
                binding.notesTitleTextView.text.toString().isEmpty() -> {
                    Toast.makeText(this, "Please fill Title", Toast.LENGTH_SHORT).show()
                }
                binding.notesContentTextView.text.toString().isEmpty() -> {
                    Toast.makeText(this, "Please fill some text", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val title = binding.notesTitleTextView.text.toString()
                    val content = binding.notesContentTextView.text.toString()
                    GlobalScope.launch(Dispatchers.IO) {
                        db.notesDao().updateById(title, content, id)
                        val intent = Intent(this@EditNotesActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }


        binding.btnDeleteNote.setOnClickListener {
            binding.btnFormCompleted.isEnabled = false
            GlobalScope.launch(Dispatchers.IO) {
                db.notesDao().deleteNoteById(id)
                val intent = Intent(this@EditNotesActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}