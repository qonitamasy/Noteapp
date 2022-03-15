package com.qonita.noteapp.fragment

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.view.ViewParent
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.qonita.noteapp.R
import com.qonita.noteapp.data.model.NoteData
import com.qonita.noteapp.data.model.Priority


class SharedViewModels(application: Application): AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(noteData: List<NoteData>){
        emptyDatabase.value = noteData.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
           when(position){
                   0 -> {
                       (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red))
                   }
               1 -> {
                   (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow))
               }
               2 -> {
                   (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green))
               }
           }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }

    fun verifyDataFromUser(title: String, desc :String): Boolean{
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desc)){
            false
        }else !(title.isEmpty() || desc.isEmpty())
    }

    fun parsePriority(priority: String): Priority {
        return  when (priority){
            "High Priority" -> {
                Priority.HIGH
            }
            "Medium Priority" ->{
                Priority.MEDIUM
            }
            "Low Priority" ->{
                Priority.LOW
            }
            else -> {
                Priority.LOW
            }
        }
    }
}