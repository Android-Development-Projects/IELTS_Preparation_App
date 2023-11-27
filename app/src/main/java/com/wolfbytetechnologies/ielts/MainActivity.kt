package com.wolfbytetechnologies.ielts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wolfbytetechnologies.ielts.databinding.ActivityMainBinding
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel
import com.wolfbytetechnologies.ielts.viewModel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        dashboardViewModel.saveTitle(this, getString(R.string.ielts_preparation_actionbar))
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.getToolbarTITLE(this)?.observe(this, Observer {
            binding.toolbar.tvActionBarHeading.text = it.toString()
        })
        mainViewModel.getButtonVisibility(this)?.observe(this) {
            binding.toolbar.btnGoBack.isVisible = it
            binding.toolbar.btnIconActionBar.isVisible = !it
        }

        binding.toolbar.btnGoBack.setOnClickListener {
            super.onBackPressed()
        }

    }


}
