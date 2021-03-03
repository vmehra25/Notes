package com.vedant.notes

import androidx.room.*
import com.vedant.notes.models.Notes

@Dao
interface NotesDao {
    @Insert
    suspend fun insert(note: Notes)

    @Insert
    suspend fun insert(list: List<Notes>)

    @Delete
    suspend fun delete(note: Notes)

    @Query("select * from Notes")
    suspend fun getAllNotes(): List<Notes>

    @Query("select * from Notes where title like :title")
    suspend fun searchByTitle(title:String): List<Notes>

    @Query("update Notes set title=:title, content=:content where id=:id")
    suspend fun updateById(title: String, content:String, id:Long)

    @Query("delete from Notes where id=:id")
    suspend fun deleteNoteById(id:Long)
}