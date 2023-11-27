package com.wolfbytetechnologies.ielts.ui.listening.listeningTests.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wolfbytetechnologies.ielts.R
import com.wolfbytetechnologies.ielts.databinding.FragmentListeningTestMainTopicsBinding
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel
import com.wolfbytetechnologies.ielts.ui.listening.listeningTests.adapter.ListeningTestMainTopicAdapter
import com.wolfbytetechnologies.ielts.ui.listening.listeningTests.viewModel.ListeningTestViewModel
import com.wolfbytetechnologies.ielts.ui.listening.listeningTests.viewModel.ListeningTestsViewModelFactory
import com.wolfbytetechnologies.ielts.utils.helpers.JsonUtils
import kotlinx.coroutines.launch

class ListeningTestMainTopicsFragment : Fragment() {
    private lateinit var binding: FragmentListeningTestMainTopicsBinding
    private lateinit var listeningTestViewModel: ListeningTestViewModel
    private lateinit var listeningTestsViewModelFactory: ListeningTestsViewModelFactory
    private var listeningTestJson : String? = ""
    private lateinit var listeningTestMainTopicAdapter : ListeningTestMainTopicAdapter
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListeningTestMainTopicsBinding.inflate(layoutInflater)

        listeningTestJson = JsonUtils.getJsonDataFromAsset(
            requireContext(),
            "ielts_test/ielts_test_listening.json"
        )
        listeningTestsViewModelFactory = ListeningTestsViewModelFactory(listeningTestJson!!)
        listeningTestViewModel = ViewModelProvider(
            this,
            listeningTestsViewModelFactory
        )[ListeningTestViewModel::class.java]
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
          val itemList =   listeningTestViewModel.listeningTestItemList
                listeningTestMainTopicAdapter = ListeningTestMainTopicAdapter(itemList){clickedItem->
                    onClick(clickedItem)
                }
                binding.apply {
                    rvListeningTestMainTopics.apply {
                        adapter = listeningTestMainTopicAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            }
    }

    private fun onClick(clickedItem: String) {
        findNavController().navigate(ListeningTestMainTopicsFragmentDirections.actionListeningTestMainTopicsFragmentToListeningTestMainTopicExplanationFragment(
            clickedItem
        ))
    }



    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.listening_test_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }

}