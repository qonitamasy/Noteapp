package com.qonita.noteapp.fragment.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.qonita.noteapp.fragment.adapter.ListAdapter
import com.qonita.noteapp.fragment.SharedViewModels
import com.qonita.noteapp.R
import com.qonita.noteapp.data.model.NoteData
import com.qonita.noteapp.data.viewModelsData.NotesViewModel
import com.qonita.noteapp.databinding.FragmentListBinding
import com.qonita.noteapp.hideKeyboard
import jp.wasabeef.recyclerview.animators.LandingAnimator


class ListFragment : Fragment() , SearchView.OnQueryTextListener{

    private val mNoteViewModel : NotesViewModel by viewModels()
    private val adapter : ListAdapter by lazy { ListAdapter() }
    private val mSharedViewModels : SharedViewModels by viewModels()
    private var _listBinding : FragmentListBinding? = null
    private val listBinding get() = _listBinding !!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _listBinding = FragmentListBinding.inflate(inflater, container, false)
        listBinding.lifecycleOwner = this
        listBinding.mSharedViewModel = mSharedViewModels

        setUpRecycleView()

        mNoteViewModel.getAllData.observe(viewLifecycleOwner, {data ->
            mSharedViewModels.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })
        setHasOptionsMenu(true)
        hideKeyboard(requireActivity())
        return listBinding.root
    }

    private fun setUpRecycleView() {
        listBinding.rvList.adapter = adapter
        listBinding.rvList.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        listBinding.rvList.itemAnimator = LandingAnimator().apply {
            addDuration = 300
        }

        swipeToDelete(listBinding.rvList)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object :swipeToDelete(){
            override  fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int ){
                val deletedItem = adapter.datalist[viewHolder.adapterPosition]
                mNoteViewModel.deleteData(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                restoreDeleteData (viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    private fun restoreDeleteData(view : View, deletedItem: NoteData) {
        val snackbar = Snackbar.make(
            view, "Delete '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("Undo"){
            mNoteViewModel.insertData(deletedItem)
        }
        snackbar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete_all -> confirmRemoveAll()
            R.id.menu_priority_high -> mNoteViewModel.sortByHighPriority.observe(this, {
                adapter.setData(it)
            })
            R.id.menu_priority_low -> mNoteViewModel.sortByLowPriority.observe(this, {
                adapter.setData(it)})
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoveAll() {
       AlertDialog.Builder(requireContext())
           .setTitle("Delete All?")
           .setMessage("Are you sure want to remove all ?")
           .setPositiveButton("Yes"){_, _ ->
               mNoteViewModel.deleteAllData()
               Toast.makeText(requireContext(), "Successfully Remove All",
               Toast.LENGTH_SHORT
               ).show()
           }
           .setNegativeButton("No", null)
           .create()
           .show()
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
       if (query != null){
           searchThroughDatabase(query)
       }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"
        mNoteViewModel.searchDatabase(searchQuery).observe(
            this, {
                List -> List.let { adapter.setData(it) }
            })
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            searchThroughDatabase(query)
        }
        return  true
    }

    override fun onDestroy() {
        _listBinding = null
        super.onDestroy()
    }

}