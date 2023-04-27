package com.mkrlabs.pmisdefence.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.mkrlabs.pmisdefence.adapter.MyPagerAdapter
import com.mkrlabs.pmisdefence.databinding.FragmentPrjecetDetailsBinding
import com.mkrlabs.pmisdefence.fragment.project_details_tab.ChatTabFragment
import com.mkrlabs.pmisdefence.fragment.project_details_tab.OverviewTabFragment
import com.mkrlabs.pmisdefence.fragment.project_details_tab.TeacherTaskAddFragment
import com.mkrlabs.pmisdefence.model.Project
import com.mkrlabs.pmisdefence.util.CommonFunction


class ProjectDetailsFragment : Fragment() {


    lateinit var binding: FragmentPrjecetDetailsBinding
    val   project : ProjectDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrjecetDetailsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MyPagerAdapter(parentFragmentManager, lifecycle)
        adapter.addFragment(OverviewTabFragment(), "Overview")
        adapter.addFragment(TeacherTaskAddFragment(project.project.projectUID), "Task")
        adapter.addFragment(ChatTabFragment(), "Chat")

        binding.tabViewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.tabViewPager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()

        binding.projectDetailsBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}