package com.example.testcft

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.testcft.objects.Const.KEY_HISTORY
import com.example.testcft.objects.SharedPreferencesExtended.sharedPreferences
import com.example.testcft.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var backPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.binLookupFragment,
            R.id.binLookupHistoryFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_layout)
        supportActionBar?.elevation = 0f
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        sharedPreferences = getSharedPreferences(KEY_HISTORY, MODE_PRIVATE)
        viewModel.loadHistory()
        observers()
    }

    private fun observers() {
        viewModel.coordinates.observe(this) { coordinates ->
            if (coordinates != null) {
                val gmmIntentUri: Uri = Uri.parse(coordinates)
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }

        viewModel.message.observe(this) { message ->
            if(message != "")
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (findNavController(R.id.nav_host_fragment_activity_main).navigateUp().not()) {
            if (backPressed) {
                backPressed = false
                finishAffinity()
            } else {
                backPressed = true

                Handler().postDelayed(Runnable {
                    backPressed = false
                }, 3000)

                Toast
                    .makeText(this, "Press again to exit", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}