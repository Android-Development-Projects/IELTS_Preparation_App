package com.wolfbytetechnologies.ielts.ui.dashboard.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wolfbytetechnologies.ielts.R
import com.wolfbytetechnologies.ielts.databinding.CustomDialogLayoutBinding
import com.wolfbytetechnologies.ielts.databinding.FragmentDashboardBinding
import com.wolfbytetechnologies.ielts.ui.dashboard.adapter.DashboardAdapter
import com.wolfbytetechnologies.ielts.ui.dashboard.repo.MainDashboardItemsRepo
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel


class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var mainDashboardItemsRepo: MainDashboardItemsRepo
    private lateinit var dashboardAdapter: DashboardAdapter
    private var mInterstitialAd: InterstitialAd? = null
    private val tag = "AdLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressed()
        loadInterstitialAd()
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {

                        val customDialogView = LayoutInflater.from(requireContext())
                            .inflate(R.layout.custom_dialog_layout, null)
                        val customDialogViewBinding =
                            CustomDialogLayoutBinding.bind(customDialogView)
                        val alertDialogBuilder = MaterialAlertDialogBuilder(
                            requireContext(),
                            R.style.MyRounded_MaterialComponents_MaterialAlertDialog
                        ).setView(customDialogView).setCancelable(false)

                        val alertDialog = alertDialogBuilder.show()

                        customDialogViewBinding.btnNo.setOnClickListener {
                            if (mInterstitialAd != null) {
                                showInterstitialAd()
                            } else {
                                Log.d("TAG", "The interstitial ad wasn't ready yet.")
                            }
                            alertDialog.dismiss()
                        }
                        customDialogViewBinding.btnYes.setOnClickListener {
                            if (mInterstitialAd != null) {
                                showInterstitialAd()
                            } else {
                                requireActivity().finish()
                            }
                            requireActivity().finish()
                        }
                    }
                })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        binding.progressBar.isVisible = false
        // Inflate the layout for this fragment
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainDashboardItemsRepo = MainDashboardItemsRepo(requireContext())
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        dashboardViewModel.addItemsToDashboard(mainDashboardItemsRepo.getDashboardItems())

        observeDashboardItems()

        binding.apply {
            btnVocabulary.setOnClickListener {
                if (mInterstitialAd != null) {
                    showInterstitialAd()
                } else {
                    Log.d(tag, "The interstitial ad wasn't ready yet.")
                }
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToVocabularyTopicsFragment())
            }
            btnTips.setOnClickListener {
                if (mInterstitialAd != null) {
                    showInterstitialAd()
                } else {
                    Log.d(tag, "The interstitial ad wasn't ready yet.")
                }
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToIeltsTipsFragment())
            }
            btnGrammar.setOnClickListener {
                if (mInterstitialAd != null) {
                    showInterstitialAd()
                } else {
                    Log.d(tag, "The interstitial ad wasn't ready yet.")
                }
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToGrammarHomeFragment())
            }
        }
    }

    private fun showInterstitialAd() {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
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

        mInterstitialAd?.show(requireActivity())
    }

    private fun observeDashboardItems() {
        val dashBoardItemList = dashboardViewModel.dashboardItems

        dashboardAdapter = DashboardAdapter(dashBoardItemList) { adapterPosition ->
            onClick(adapterPosition)
        }
        binding.rvMainDashboard.apply {
            adapter = dashboardAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        }

    }

    private fun onClick(adapterPosition: Int) {
        when (adapterPosition) {
            0 -> {
                if (mInterstitialAd != null) {
                    showInterstitialAd()
                } else {
                    Log.d(tag, "The interstitial ad wasn't ready yet.")
                }
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToReadingLessonHomeFragment())
            }

            1 -> {
                if (mInterstitialAd != null) {
                    showInterstitialAd()
                } else {
                    Log.d(tag, "The interstitial ad wasn't ready yet.")
                }
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToReadingTestMainTopicsFragment())
            }

            2 -> {
                if (mInterstitialAd != null) {
                    showInterstitialAd()
                } else {
                    Log.d(tag, "The interstitial ad wasn't ready yet.")
                    loadInterstitialAd()
                }
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToWritingLessonHomeFragment())
            }

            3 -> {
                if (mInterstitialAd != null) {
                    showInterstitialAd()
                } else {
                    Log.d(tag, "The interstitial ad wasn't ready yet.")
                }
                displayProgressIfSlow()
                findNavController().navigate(
                    DashboardFragmentDirections.actionDashboardFragmentToWritingTasksFragment(
                        "Writing Task 1"
                    )
                )
            }

            4 -> {
                if (mInterstitialAd != null) {
                    showInterstitialAd()
                } else {
                    Log.d(tag, "The interstitial ad wasn't ready yet.")
                }
                displayProgressIfSlow()
                findNavController().navigate(
                    DashboardFragmentDirections.actionDashboardFragmentToWritingTasksFragment(
                        "Writing Task 2"
                    )
                )
            }

            5 -> {
                if (mInterstitialAd != null) {
                    showInterstitialAd()
                } else {
                    Log.d(tag, "The interstitial ad wasn't ready yet.")
                }
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToSpeakingLessonHomeFragment())
            }

            6 -> {
                if (mInterstitialAd != null) {
                    showInterstitialAd()
                } else {
                    Log.d(tag, "The interstitial ad wasn't ready yet.")
                }
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToSpeakingTestHomeFragment())
            }

            7 -> {
                if (mInterstitialAd != null) {
                    showInterstitialAd()
                } else {
                    Log.d(tag, "The interstitial ad wasn't ready yet.")
                }
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToListeningLessonHomeFragment())
            }

            8 -> {
                if (mInterstitialAd != null) {
                    showInterstitialAd()
                } else {
                    Log.d(tag, "The interstitial ad wasn't ready yet.")
                }
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToListeningTestMainTopicsFragment())
            }
        }
    }

    private fun displayProgressIfSlow() {
        binding.progressBar.isVisible =
            Build.VERSION.SDK_INT == Build.VERSION_CODES.N || Build.VERSION.SDK_INT == 27 || Build.VERSION.SDK_INT == 26
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(
            requireContext(), getString(R.string.ielts_preparation_actionbar)
        )
        dashboardViewModel.saveButtonVisibility(requireContext(), false)
        loadInterstitialAd()
    }


    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(),
            "ca-app-pub-1254010252723206/6027590401",
            adRequest,
            object : InterstitialAdLoadCallback() {
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
}