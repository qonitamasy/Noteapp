package com.qonita.noteapp.data.viewModelsData

import android.app.Application
import android.app.SearchManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.qonita.noteapp.data.model.NoteData
import com.qonita.noteapp.data.model.NoteDatabase
import com.qonita.noteapp.data.repository.Noterepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel (application: Application): AndroidViewModel(application) {
    private val noteDao = NoteDatabase.getDatabase(application).noteDao()
    private val repository: Noterepository
    val getAllData: LiveData<List<NoteData>>
    val sortByHighPriority: LiveData<List<NoteData>>
    val sortByLowPriority: LiveData<List<NoteData>>

    init {
        repository = Noterepository(noteDao)
        getAllData = repository.getAllData
        sortByHighPriority = repository.sortByHighPriority
        sortByLowPriority = repository.sortByLowPriority
    }


    fun insertData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(noteData)
        }
    }
    fun updateData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(noteData)
        }
    }


    fun deleteData(noteData: NoteData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(noteData)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }
    fun searchDatabase(searchQuery: String): LiveData<List<NoteData>>{
        return  repository.searchData(searchQuery)
        }
}