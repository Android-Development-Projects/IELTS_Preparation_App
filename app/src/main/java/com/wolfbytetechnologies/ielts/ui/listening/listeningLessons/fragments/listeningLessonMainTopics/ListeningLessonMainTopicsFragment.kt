package com.wolfbytetechnologies.ielts.ui.listening.listeningLessons.fragments.listeningLessonMainTopics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.wolfbytetechnologies.ielts.data.LessonItems
import com.wolfbytetechnologies.ielts.databinding.FragmentListeningLessonMainTopicsBinding
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel
import com.wolfbytetechnologies.ielts.ui.listening.listeningLessons.adapter.ListeningMainTopicsAdapter

class ListeningLessonMainTopicsFragment : Fragment() {
    private lateinit var binding: FragmentListeningLessonMainTopicsBinding
    private val args: ListeningLessonMainTopicsFragmentArgs by navArgs()
    private var listeningMainTopics = MutableLiveData<Array<LessonItems>>()
    private var toolBarText : String = ""
    private lateinit var dashboardViewModel: DashboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListeningLessonMainTopicsBinding.inflate(layoutInflater)

        listeningMainTopics.value = args.receivedItemsArray
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listeningMainTopics.observe(viewLifecycleOwner){
            it.forEach {
                toolBarText = it.mainTopic.toString()
            }
            val listeningMainTopicAdapter = ListeningMainTopicsAdapter(it){ currentTopicDetails->
                findNavController().navigate(ListeningLessonMainTopicsFragmentDirections.actionListeningLessonMainTopicsFragmentToListeningMainTopicExplanationFragment(
                    currentTopicDetails
                ))
            }
            binding.rvListeningLessonMainTopics.apply {
                adapter = listeningMainTopicAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

    }


    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), toolBarText)
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }

}
