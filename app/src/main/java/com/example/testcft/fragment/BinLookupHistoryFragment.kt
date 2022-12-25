package com.example.testcft.fragment

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.example.testcft.MainActivity
import com.example.testcft.R
import com.example.testcft.adapter.BinLookupHistoryAdapter
import com.example.testcft.databinding.FragmentBinLookupHistoryBinding
import com.example.testcft.model.History
import com.example.testcft.objects.SharedPreferencesExtended.sharedPreferences
import com.example.testcft.viewmodel.MainViewModel

class BinLookupHistoryFragment: Fragment(R.layout.fragment_bin_lookup_history) {
    private var _binding: FragmentBinLookupHistoryBinding? = null
    private val binding get() = _binding!!
    private val historyAdapter = BinLookupHistoryAdapter()
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as MainActivity).supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.arrow_back)
            setDisplayHomeAsUpEnabled(true)

        }

        _binding = FragmentBinLookupHistoryBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapter = historyAdapter

        setupMenu()
        observers()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar
            ?.customView
            ?.findViewById<TextView>(R.id.titleBar)?.text = "History"
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_delete, menu)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.delete -> {
                        sharedViewModel.historyList.value?.clear()
                        sharedPreferences.edit().clear().apply()
                        historyAdapter.setData(sharedViewModel.historyList.value as ArrayList<History>)
                    }
                    else -> (activity as MainActivity).onBackPressed()
                }

                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }



    private fun observers() {
        sharedViewModel.historyList.observe(viewLifecycleOwner) { historyList ->
            if (!historyList.isNullOrEmpty())
                historyAdapter.setData(historyList as ArrayList<History>)
        }
    }
}