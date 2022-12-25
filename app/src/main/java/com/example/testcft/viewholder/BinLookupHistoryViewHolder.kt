package com.example.testcft.viewholder

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.testcft.databinding.RowBinLookupHistoryBinding
import com.example.testcft.model.History

class BinLookupHistoryViewHolder(private val binding: RowBinLookupHistoryBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(history: History) {
        binding.bin = history.bin
        binding.time = history.time
        binding.executePendingBindings()
    }

    init {
        binding.root.setOnClickListener {
            val clipboard =
                binding.root.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            clipboard.setPrimaryClip(
                ClipData.newPlainText("BIN", binding.bin)
            )

            Toast.makeText(binding.root.context, "BIN copied", Toast.LENGTH_SHORT).show()
        }
    }
}