package com.mkrlabs.pmisdefence.fragment.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.databinding.FragmentTeacherAddMemberBinding


class TeacherAddMemberFragment : Fragment() {


    lateinit var binding : FragmentTeacherAddMemberBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeacherAddMemberBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addMemberBackButton.setOnClickListener {
            findNavController().navigateUp()
        }


    }

}