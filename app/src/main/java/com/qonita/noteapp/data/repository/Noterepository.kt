package com.qonita.noteapp.data.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.qonita.noteapp.data.model.NoteDao
import com.qonita.noteapp.data.model.NoteData

class Noterepository (private  val noteDao: NoteDao){

    val getAllData :  LiveData<List<NoteData>> = noteDao. sortByHighPriority()
    val sortByHighPriority : LiveData<List<NoteData>> = noteDao.sortByHighPriority()
    val sortByLowPriority : LiveData<List<NoteData>> = noteDao.sortByHighPriority()

    fun insertData(noteData: NoteData){
        noteDao.insertData(noteData)
    }
    fun updateData(noteData: NoteData) {
        noteDao.updateData(noteData)
    }
    fun deleteData(noteData: NoteData){
        noteDao.deleteData(noteData)
    }
    fun deleteAllData(){
        noteDao.deleteAllData()
    }
    fun searchData(searchQuery: String) : LiveData<List<NoteData>>{
        return  noteDao.searchDatabase(searchQuery)
    }
}


