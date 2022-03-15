package com.qonita.noteapp.fragment.list

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class swipeToDelete: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }
}