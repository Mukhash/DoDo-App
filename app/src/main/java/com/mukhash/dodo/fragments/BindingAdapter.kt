package com.mukhash.dodo.fragments

import android.renderscript.RenderScript
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavArgs
import androidx.navigation.NavArgsLazy
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mukhash.dodo.R
import com.mukhash.dodo.data.models.DoDoData
import com.mukhash.dodo.data.models.Priority
import com.mukhash.dodo.fragments.list.ListFragmentDirections
import com.mukhash.dodo.fragments.update.UpdateFragmentArgs
import com.mukhash.dodo.fragments.update.UpdateFragmentDirections

class BindingAdapter {

    companion object {
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            when (emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            when (priority) {
                Priority.HIGH -> {
                    view.setSelection(0)
                }
                Priority.MEDIUM -> {
                    view.setSelection(1)
                }
                Priority.LOW -> {
                    view.setSelection(2)
                }
            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(view: CardView, priority: Priority) {
            when (priority) {
                Priority.HIGH -> {
                    view.setCardBackgroundColor(view.context.getColor(R.color.red))
                }
                Priority.MEDIUM -> {
                    view.setCardBackgroundColor(view.context.getColor(R.color.yellow))
                }
                Priority.LOW -> {
                    view.setCardBackgroundColor(view.context.getColor(R.color.green))
                }
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun setDataToUpdateFragment(view: ConstraintLayout, currentItem: DoDoData) {
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }

    }

}