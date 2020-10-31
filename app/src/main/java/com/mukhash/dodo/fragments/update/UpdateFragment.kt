package com.mukhash.dodo.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.renderscript.RenderScript
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mukhash.dodo.R
import com.mukhash.dodo.data.models.DoDoData
import com.mukhash.dodo.data.models.Priority
import com.mukhash.dodo.data.viewmodel.DoDoViewModel
import com.mukhash.dodo.data.viewmodel.SharedViewModel
import com.mukhash.dodo.databinding.FragmentUpdateBinding
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mDoDoViewModel: DoDoViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding?.args = args

        setHasOptionsMenu(true)

        binding?.currentSpinnerPriorities?.onItemSelectedListener = mSharedViewModel.listener

        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = current_title_et.text.toString()
        val description = current_description_et.text.toString()
        val priority = current_spinner_priorities.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
            val updatedItem = DoDoData(
                args.currentItem.id,
                title = title,
                description = description,
                priority = mSharedViewModel.parsePriority(priority)
            )

            mDoDoViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun confirmItemRemoval() {
        val alertBuilder = AlertDialog.Builder(requireContext())
        alertBuilder.apply {
            setPositiveButton("Yes") { _, _ ->
                mDoDoViewModel.deleteData(args.currentItem)
                Toast.makeText(
                    requireContext(),
                    "Data deleted: '${args.currentItem.title}'",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            setNegativeButton("No") { _, _ -> }
            setTitle("Delete ${args.currentItem.title}?")
            setMessage("Are you sure you want to delete '${args.currentItem.title}'?")
            create()
            show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}