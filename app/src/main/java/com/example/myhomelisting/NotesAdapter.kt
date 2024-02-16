package com.example.myhomelisting

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter (private var listes: List<notes>, context: Context):
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

        // Delete Data
        private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.tv_title_text)
        val descTextView: TextView = itemView.findViewById(R.id.tv_description_text)
        val updateButon: ImageView = itemView.findViewById(R.id.iv_update)
        val deleteButon: ImageView = itemView.findViewById(R.id.iv_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false))
    }

    override fun getItemCount(): Int {
       return listes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Read data
        val list = listes[position]
        holder.titleTextView.text = list.title
        holder.descTextView.text = list.desc

        // Update Data (mengambil id karena merupakan primary key)
        holder.updateButon.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateNotesActivity::class.java).apply {
                putExtra("note_id", list.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        // Delete Data
        holder.deleteButon.setOnClickListener{
            db.deleteData(list.id)
            refreshData(db.getAllListing())
            Toast.makeText(holder.itemView.context,"Data deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newNotes: List<notes>){
        listes = newNotes
        notifyDataSetChanged()
    }
}