package com.wolfbytetechnologies.ielts.ui.ieltsTips.fragment.ieltsTipsHome

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
import com.wolfbytetechnologies.ielts.databinding.FragmentIeltsTipsBinding
import com.wolfbytetechnologies.ielts.ui.dashboard.viewModel.DashboardViewModel
import com.wolfbytetechnologies.ielts.ui.ieltsTips.adapter.IeltsTipsAdapter
import com.wolfbytetechnologies.ielts.ui.ieltsTips.data.IeltsTipsItems
import com.wolfbytetechnologies.ielts.ui.ieltsTips.viewModel.IeltsTipsViewModel
import com.wolfbytetechnologies.ielts.ui.ieltsTips.viewModel.IeltsTipsViewModelFactory
import com.wolfbytetechnologies.ielts.utils.helpers.JsonUtils
import kotlinx.coroutines.launch


class IeltsTipsFragment : Fragment() {
    private lateinit var binding: FragmentIeltsTipsBinding
    private var tipsJson: String? = ""
    private lateinit var ieltsTipsViewModel: IeltsTipsViewModel
    private lateinit var ieltsTipsAdapter: IeltsTipsAdapter
    private lateinit var dashboardViewModel: DashboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIeltsTipsBinding.inflate(layoutInflater)
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            tipsJson = JsonUtils.getJsonDataFromAsset(
                requireContext(),
                "ielts_test/tips/tips.json"
            )
            val ieltsTipsFactory = IeltsTipsViewModelFactory(tipsJson)
            ieltsTipsViewModel = ViewModelProvider(
                this@IeltsTipsFragment,
                ieltsTipsFactory
            )[IeltsTipsViewModel::class.java]
            val tipsList = ieltsTipsViewModel.ieltsTips
            observeTipsData(tipsList)
        }
    }

    private fun observeTipsData(tipsList: MutableList<IeltsTipsItems>) {
        ieltsTipsAdapter = IeltsTipsAdapter(requireContext(), tipsList){ currentItem ->
            onClick(currentItem)
        }
        binding.rvIeltsTipsHome.apply {
            adapter = ieltsTipsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onClick(currentItem: IeltsTipsItems) {
        findNavController().navigate(IeltsTipsFragmentDirections.actionIeltsTipsFragmentToIeltsTipsExplanationFragment(
        currentItem
        ))
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.tips_actionBar))
        dashboardViewModel.saveButtonVisibility(requireContext(),true)
    }

}