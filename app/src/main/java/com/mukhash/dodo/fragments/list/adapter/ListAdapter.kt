package com.mukhash.dodo.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mukhash.dodo.data.models.DoDoData
import com.mukhash.dodo.databinding.RowLayoutBinding

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var adapterList = emptyList<DoDoData>()

    class MyViewHolder(private val binding: RowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(doDoData: DoDoData) {
            binding.doDoData = doDoData
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(
                    binding
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(
            parent
        )
    }

    override fun getItemCount() = adapterList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(adapterList[position])
    }

    fun setData(doDoData: List<DoDoData>) {
        val mDiffUtil = DiffUtil(adapterList, doDoData)
        val mDiffUtilResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(mDiffUtil)
        this.adapterList = doDoData
        mDiffUtilResult.dispatchUpdatesTo(this)
    }
}