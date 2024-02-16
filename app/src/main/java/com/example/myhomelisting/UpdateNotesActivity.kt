package com.example.myhomelisting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myhomelisting.databinding.ActivityUpdateNotesBinding

class UpdateNotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNotesBinding
    private lateinit var db: NotesDatabaseHelper
    private var listId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        listId = intent.getIntExtra("note_id", -1)
        if (listId == -1){
            finish()
            return
        }

        val list = db.getListingById(listId)
        binding.updateTitleEditText.setText(list.title)
        binding.updateDescriptionEditText.setText(list.desc)

        binding.updateSaveButton.setOnClickListener{
            val newTitle = binding.updateTitleEditText.text.toString()
            val newDesc = binding.updateDescriptionEditText.text.toString()
            val updateNotes = notes(listId, newTitle, newDesc)
            db.updateListing(updateNotes)
            finish()
            Toast.makeText(this,"Changes saved", Toast.LENGTH_SHORT).show()
        }

    }
}