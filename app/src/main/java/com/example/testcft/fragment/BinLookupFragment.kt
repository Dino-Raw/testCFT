package com.example.testcft.fragment

import android.annotation.SuppressLint
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
import androidx.navigation.fragment.findNavController
import com.example.testcft.MainActivity
import com.example.testcft.R
import com.example.testcft.databinding.FragmentBinLookupBinding
import com.example.testcft.viewmodel.MainViewModel


class BinLookupFragment: Fragment(R.layout.fragment_bin_lookup) {
    private var _binding: FragmentBinLookupBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBinLookupBinding.inflate(inflater)
        binding.viewModel = sharedViewModel
        binding.lifecycleOwner = viewLifecycleOwner



        setupMenu()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar
            ?.customView
            ?.findViewById<TextView>(R.id.titleBar)?.text = "Bin Lookup"
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_history, menu)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                findNavController()
                    .navigate(R.id.action_binLookupFragment_to_binLookupHistoryFragment)
                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    @SuppressLint("CommitPrefEdits")
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}