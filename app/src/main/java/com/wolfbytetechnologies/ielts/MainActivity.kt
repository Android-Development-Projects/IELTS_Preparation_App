package com.wolfbytetechnologies.ielts

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.wolfbytetechnologies.ielts.databinding.ActivityMainBinding
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel
import com.wolfbytetechnologies.ielts.viewModel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var myAdView : AdView
    private var mInterstitialAd: InterstitialAd? = null
    private val tag = "AdLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadBannerAd()
        loadInterstitialAd()

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

    override fun onDestroy() {
        mInterstitialAd?.fullScreenContentCallback = null
        super.onDestroy()
    }


    private fun loadBannerAd(){
        MobileAds.initialize(this) {}

        myAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        myAdView.loadAd(adRequest)

        myAdView.adListener = object: AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.d(tag, "Banner Clicked")
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Log.d(tag, "Banner Closed")
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
                Log.d(tag, "Banner Failed to load because : $adError")
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d(tag, "Banner Ad Loaded Successfully")
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d(tag, "Banner Ad Opened")
            }
        }
    }

    private fun loadInterstitialAd(){
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(tag, adError.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(tag, "Ad was Loaded")
                mInterstitialAd = interstitialAd
            }
        })
    }

    private fun showInterstitialAd() {
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(tag, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(tag, "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e(tag, "Ad failed to show fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(tag, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(tag, "Ad showed fullscreen content.")
            }
        }

        mInterstitialAd?.show(this)
    }

}
