package com.qonita.noteapp.fragment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.qonita.noteapp.data.model.NoteData

class NoteDiffUtil (
    private  val oldList : List<NoteData>,
            private val newsList : List<NoteData>,
): DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return  oldList.size
    }

    override fun getNewListSize(): Int {
        return newsList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition]=== newsList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition].id == newsList[newItemPosition].id
                && oldList[oldItemPosition].title == newsList[newItemPosition].title
                && oldList[oldItemPosition].description == newsList[newItemPosition].description
                && oldList[oldItemPosition].priority == newsList[newItemPosition].priority
    }

}