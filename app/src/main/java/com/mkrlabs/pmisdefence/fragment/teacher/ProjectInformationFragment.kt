package com.mkrlabs.pmisdefence.fragment.teacher

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.databinding.FragmentProjectInformationBinding
import com.mkrlabs.pmisdefence.util.CommonFunction
import dagger.hilt.android.qualifiers.ApplicationContext


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


        binding.projectInformationBackButton.setOnClickListener {
            findNavController().navigateUp()

        }


        binding.projectInformationNextButton.setOnClickListener{


            val projectName = binding.createProjectProjectName.text.toString()
            val projectId = binding.createProjectProjectId.text.toString()




            if (TextUtils.isEmpty(projectName)){
                binding.createProjectProjectName.error= "required"
                return@setOnClickListener
            }

            if (binding.createProjectProjectTypeGroup.checkedRadioButtonId == -1){
                CommonFunction.errorToast(view.context,"please select project type")
                return@setOnClickListener

            }

            if (TextUtils.isEmpty(projectId)){
                binding.createProjectProjectId.error= "required"
                return@setOnClickListener
            }

            CommonFunction.successToast(view.context,"All are okay")






        }






    }
}