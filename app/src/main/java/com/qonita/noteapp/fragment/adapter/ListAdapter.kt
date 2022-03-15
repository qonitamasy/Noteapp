package com.qonita.noteapp.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.qonita.noteapp.data.model.NoteData
import com.qonita.noteapp.databinding.RowLayoutItemBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var datalist = emptyList<NoteData>()
    class MyViewHolder (val binding: RowLayoutItemBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind (noteData: NoteData){
            binding.noteData = noteData
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) : MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutItemBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder. from(parent)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = datalist[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = datalist.size
    fun  setData(noteData: List<NoteData>){
        val noteDiffUtil = NoteDiffUtil(datalist, noteData)
        val noteDiffResult = DiffUtil.calculateDiff(noteDiffUtil)
        this.datalist = noteData
        noteDiffResult.dispatchUpdatesTo(this)
        TODO("Not yet implemented")
    }
}