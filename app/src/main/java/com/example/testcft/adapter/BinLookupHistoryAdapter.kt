package com.example.testcft.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testcft.databinding.RowBinLookupHistoryBinding
import com.example.testcft.model.History
import com.example.testcft.viewholder.BinLookupHistoryViewHolder

class BinLookupHistoryAdapter: RecyclerView.Adapter<BinLookupHistoryViewHolder>() {
    var historyList = ArrayList<History>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(historyList: ArrayList<History>) {
        this.historyList.clear()
        this.historyList.addAll(historyList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinLookupHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowBinLookupHistoryBinding.inflate(layoutInflater)
        return BinLookupHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BinLookupHistoryViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount() = historyList.size
}