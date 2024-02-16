package com.example.myhomelisting

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDatabaseHelper (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "listapps.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "alllist"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESC = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
       val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DESC TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNotes(list: notes){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, list.title)
            put(COLUMN_DESC, list.desc)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllListing(): List<notes>{
        val homeList = mutableListOf<notes>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC))

            val list = notes(id, title, description)
            homeList.add(list)
        }
        cursor.close()
        return homeList
    }

    fun updateListing(list: notes){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, list.title)
            put(COLUMN_DESC, list.desc)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(list.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getListingById(listId: Int): notes{
        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $listId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToNext()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC))

        cursor.close()
        db.close()
        return notes(id, title, desc)
    }

    fun deleteData(listId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(listId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

}