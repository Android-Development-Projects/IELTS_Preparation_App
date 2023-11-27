package com.wolfbytetechnologies.ielts.ui.speaking.speakingTests.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wolfbytetechnologies.ielts.R
import com.wolfbytetechnologies.ielts.databinding.FragmentSpeakingTestHomeBinding
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel
import com.wolfbytetechnologies.ielts.ui.speaking.speakingTests.adapter.SpeakingTestHomeItemAdapter
import com.wolfbytetechnologies.ielts.ui.speaking.speakingTests.data.SpeakingTestItem
import com.wolfbytetechnologies.ielts.ui.speaking.speakingTests.viewModel.SpeakingTestViewModel
import com.wolfbytetechnologies.ielts.ui.speaking.speakingTests.viewModel.SpeakingTestViewModelFactory
import com.wolfbytetechnologies.ielts.utils.helpers.JsonUtils

class SpeakingTestHomeFragment : Fragment() {
    private lateinit var binding: FragmentSpeakingTestHomeBinding
    private lateinit var speakingTestFactory: SpeakingTestViewModelFactory
    private lateinit var speakingViewModel: SpeakingTestViewModel
    private lateinit var dashboardViewModel: DashboardViewModel
    private var speakingTestJson: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpeakingTestHomeBinding.inflate(layoutInflater)
        speakingTestJson = JsonUtils.getJsonDataFromAsset(
            requireContext(),
            "ielts_test/speaking/part_2.json"
        )
        speakingTestFactory = SpeakingTestViewModelFactory(speakingTestJson)
        speakingViewModel = ViewModelProvider(
            this,
            speakingTestFactory
        )[SpeakingTestViewModel::class.java]
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemList = speakingViewModel.speakingItemList

        val speakingTestAdapter = SpeakingTestHomeItemAdapter(
            requireContext(),
            itemList
        ) { currentItem ->
            onClick(currentItem)
        }
        binding.rvSpeakingTestMainTopics.apply {
            adapter = speakingTestAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private fun onClick(currentItem: SpeakingTestItem) {
        findNavController().navigate(
            SpeakingTestHomeFragmentDirections.actionSpeakingTestHomeFragmentToSpeakingTestExplanationFragment(
                currentItem
            )
        )
    }


    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.speaking_test_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }

}