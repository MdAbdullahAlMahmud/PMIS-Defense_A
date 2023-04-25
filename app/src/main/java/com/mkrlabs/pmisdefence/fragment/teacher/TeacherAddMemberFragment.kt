package com.mkrlabs.pmisdefence.fragment.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.databinding.FragmentTeacherAddMemberBinding
import com.mkrlabs.pmisdefence.util.CommonFunction


class TeacherAddMemberFragment : Fragment() {


    lateinit var binding : FragmentTeacherAddMemberBinding

    val project : TeacherAddMemberFragmentArgs by navArgs()
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

        CommonFunction.successToast(view.context,"Project -> "+ project.project.projectName)


    }

}