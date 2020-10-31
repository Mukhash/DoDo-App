package com.mukhash.dodo.data.viewmodel

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mukhash.dodo.R
import com.mukhash.dodo.data.models.DoDoData
import com.mukhash.dodo.data.models.Priority

class SharedViewModel(application: Application): AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(doDoData: List<DoDoData>) {
        emptyDatabase.value = doDoData.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            when(position) {
                0 -> { (parent?.getChildAt(0) as TextView?)?.setTextColor(ContextCompat.getColor(application, R.color.red))}
                1 -> { (parent?.getChildAt(0) as TextView?)?.setTextColor(ContextCompat.getColor(application, R.color.yellow))}
                2 -> { (parent?.getChildAt(0) as TextView?)?.setTextColor(ContextCompat.getColor(application, R.color.green))}
            }
        }

    }

    fun verifyDataFromUser(title: String, description: String): Boolean {
        return !(description.isEmpty())
    }

    fun parsePriority(priority: String): Priority {
        return when(priority) {
            "High" -> {
                Priority.HIGH}
            "Medium" -> {
                Priority.MEDIUM}
            "Low" -> {
                Priority.LOW}
            else -> {
                Priority.LOW}
        }
    }

}