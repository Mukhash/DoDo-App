package com.mukhash.dodo.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import com.mukhash.dodo.R
import com.mukhash.dodo.data.models.DoDoData
import com.mukhash.dodo.data.utils.hideKeyboard
import com.mukhash.dodo.data.viewmodel.DoDoViewModel
import com.mukhash.dodo.data.viewmodel.SharedViewModel
import com.mukhash.dodo.databinding.FragmentListBinding
import com.mukhash.dodo.fragments.list.adapter.ListAdapter

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val mDoDoViewModel: DoDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding

    private val adapter: ListAdapter by lazy { ListAdapter() }
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Binding
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this
        binding?.mSharedViewModel = mSharedViewModel

        //RecView
        setupRecyclerView()

        //Observing viewModel
        mDoDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        setHasOptionsMenu(true)

        hideKeyboard(requireActivity())

        return binding?.root
    }

    private fun setupRecyclerView() {
        val recyclerView = binding?.recyclerView
        recyclerView?.adapter = adapter
        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = null

        recyclerView?.let { swipeToDelete(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()
            R.id.menu_priority_high -> mDoDoViewModel.sortByHighPriority.observe(
                this,
                Observer { adapter.setData(it) })
            R.id.menu_priority_low -> mDoDoViewModel.sortByLowPriority.observe(
                this,
                Observer { adapter.setData(it) })
            R.id.menu_view ->
                if (layoutManager is StaggeredGridLayoutManager) {
                    layoutManager = LinearLayoutManager(requireContext())
                    binding?.recyclerView?.layoutManager = layoutManager
                    item.title = this.getString(R.string.view_type_grid)
                } else {
                    layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    binding?.recyclerView?.layoutManager = layoutManager
                    item.title = this.getString(R.string.view_type_list)
                }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.adapterList[viewHolder.adapterPosition]
                mDoDoViewModel.deleteData(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                //Restore
                restoreDeletedData(viewHolder.itemView, deletedItem)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: DoDoData) {
        val snackBar = Snackbar.make(
            view, "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )

        snackBar.setAction("Undo") {
            mDoDoViewModel.insertData(deletedItem)
        }
        snackBar.show()
    }

    private fun confirmRemoval() {
        val alertBuilder = AlertDialog.Builder(requireContext())
        alertBuilder.apply {
            setPositiveButton("Yes") { _, _ ->
                mDoDoViewModel.deleteAll()
                Toast.makeText(
                    requireContext(),
                    "All Data deleted",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setNegativeButton("No") { _, _ -> }
            setTitle("Delete All")
            setMessage("Are you sure you want to delete all?")
            create()
            show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"

        mDoDoViewModel.searchDatabase(searchQuery).observe(this, Observer { list ->
            list?.let {
                adapter.setData(it)
            }
        })
    }
}