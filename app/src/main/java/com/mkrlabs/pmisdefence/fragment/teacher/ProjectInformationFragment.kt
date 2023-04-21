package com.mkrlabs.pmisdefence.fragment.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.databinding.FragmentProjectInformationBinding


class ProjectInformationFragment : Fragment() {


    lateinit var binding: FragmentProjectInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProjectInformationBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.projectInformationNextButton.setOnClickListener{

            findNavController().navigate(R.id.action_projectInformationFragment_to_teacherAddMemberFragment)
        }
    }
}