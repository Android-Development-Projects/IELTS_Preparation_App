package com.wolfbytetechnologies.ielts.ui.reading.readingLessons.fragments.readingSkills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.wolfbytetechnologies.ielts.R
import com.wolfbytetechnologies.ielts.data.LessonItems
import com.wolfbytetechnologies.ielts.databinding.FragmentReadingSkillsBinding
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel
import com.wolfbytetechnologies.ielts.ui.reading.readingLessons.adapter.ReadingLessonTopicsAdapter

class ReadingSkillsFragment : Fragment() {
    private lateinit var binding: FragmentReadingSkillsBinding
    private lateinit var lessonTopicsAdapter: ReadingLessonTopicsAdapter
    private val args: ReadingSkillsFragmentArgs by navArgs()
    private lateinit var dashboardViewModel: DashboardViewModel


    private var receivedItem : Array<LessonItems> = arrayOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadingSkillsBinding.inflate(layoutInflater)
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receivedItem = args.selectedTopicItems
        lessonTopicsAdapter = ReadingLessonTopicsAdapter(receivedItem){clickedItem, position->
            onClick(clickedItem,position)

        }
        binding.rvReadingSkills.apply {
            adapter = lessonTopicsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onClick(clickedItem: LessonItems, position: String) {
        findNavController().navigate(ReadingSkillsFragmentDirections.actionReadingSkillsFragmentToReadingContentExplanationFragment(
            clickedItem,
            position
        ))
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.reading_skills_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }

}


