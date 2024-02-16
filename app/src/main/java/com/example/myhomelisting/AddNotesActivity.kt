package com.example.myhomelisting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myhomelisting.databinding.ActivityAddNotesBinding

class AddNotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNotesBinding
    

    private lateinit var db: NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val title = binding.titleEditText.text.toString().trim()
            val desc = binding.descEditText.text.toString().trim()
            val notes = notes(0, title, desc)

            if (title.isNotEmpty() && desc.isNotEmpty()){
                db.insertNotes(notes)
                finish()
                Toast.makeText(this, "notes saved", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "notes can't be  saved", Toast.LENGTH_SHORT).show()
            }


        }

    }
}