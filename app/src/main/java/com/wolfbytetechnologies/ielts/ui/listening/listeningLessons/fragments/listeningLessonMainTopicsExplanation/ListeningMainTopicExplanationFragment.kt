package com.wolfbytetechnologies.ielts.ui.listening.listeningLessons.fragments.listeningLessonMainTopicsExplanation

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.wolfbytetechnologies.ielts.R
import com.wolfbytetechnologies.ielts.data.LessonItems
import com.wolfbytetechnologies.ielts.databinding.FragmentListeningMainTopicExplanationBinding
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel

class ListeningMainTopicExplanationFragment : Fragment() {
    private lateinit var binding: FragmentListeningMainTopicExplanationBinding
    private val args: ListeningMainTopicExplanationFragmentArgs by navArgs()
    private lateinit var currentItem: LessonItems
    private lateinit var contentReceived: Spanned
    private lateinit var dashboardViewModel: DashboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListeningMainTopicExplanationBinding.inflate(layoutInflater)
        currentItem = args.currentTopicDetails

        contentReceived =   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(currentItem.content, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(currentItem.content)
        }
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvListeningLessonMainTopic.text = currentItem.mainTopic
            tvListeningContentExplanation.text = contentReceived
            tvListeningLessonSubTopic.text = currentItem.title
            tvListeningSourceDescription.text = currentItem.source
        }
    }


    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.listening_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }
}

