package com.mkrlabs.pmisdefence.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.adapter.ProjectAdapter
import com.mkrlabs.pmisdefence.databinding.FragmentHomeBinding
import com.mkrlabs.pmisdefence.databinding.NavDesignBinding
import com.mkrlabs.pmisdefence.model.ChatItem
import com.mkrlabs.pmisdefence.model.ChatTYPE
import com.mkrlabs.pmisdefence.model.User
import com.mkrlabs.pmisdefence.ui.HomeActivity
import com.mkrlabs.pmisdefence.util.CommonFunction
import com.mkrlabs.pmisdefence.util.Resource
import com.mkrlabs.pmisdefence.util.SharedPref
import com.mkrlabs.pmisdefence.view_model.AuthenticationViewModel
import com.mkrlabs.pmisdefence.view_model.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    lateinit var mAuth : FirebaseAuth
    lateinit var projectViewModel: ProjectViewModel
    lateinit var authenticationViewModel: AuthenticationViewModel

    lateinit var projectAdapter: ProjectAdapter

    lateinit var onMenuItemClickListener: OnMenuItemClickListener

    lateinit var navLayout :NavDesignBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        projectViewModel = ViewModelProvider(this)[ProjectViewModel::class.java]
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        mAuth  = FirebaseAuth.getInstance()

        //navLayout = NavDesignBinding.inflate(LayoutInflater.from(context), null,false)

        binding.homeNotification.setOnClickListener {


        }


        binding.addNewTeamHome.setOnClickListener{

            findNavController().navigate(R.id.action_homeFragment_to_projectInformationFragment)
        }


        CommonFunction.successToast(view.context,SharedPref(view.context).getLoggedInUserName())

        projectViewModel.fetchProjectList()
        projectViewModel.projectList.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideLoading()
                    response.data?.let { projectAdapter.differ.submitList(it) }
                }
                is Resource.Loading->{
                    showLoading()
                }
                is Resource.Error->{
                    hideLoading()
                    CommonFunction.successToast(view.context,response.message.toString())
                }
            }
        })

        projectAdapter.setOnProjectItemClickListener { project ->
            val projectIntent = project
            val bundle = Bundle().apply { putSerializable("project",projectIntent) }
            findNavController().navigate(R.id.action_homeFragment_to_projectDetailsFragment,bundle)
        }

        projectAdapter.setOnAddTeamMemberItemClickListener {
            val bundle = Bundle().apply {putSerializable("project",it)}
            findNavController().navigate(R.id.action_homeFragment_to_teacherAddMemberFragment,bundle)
        }


        projectAdapter.setOnGroupMessageClickListener {
            var groupChatItem = ChatItem(it.projectName,it.projectUID, it.projectDescription,
                Date().time,it.teacher_id,"'",
                ChatTYPE.GROUP)

            val bundle = Bundle().apply {
                putSerializable("chatItem",groupChatItem)
            }
            findNavController().navigate(R.id.action_homeFragment_to_chatFragment,bundle)

        }

        binding.homeNavDrawerMenu.setOnClickListener {
            (requireActivity() as HomeActivity).openNavigationDrawer()
        }




    }


    interface  OnMenuItemClickListener{
        fun OnClick()
    }

    fun hideLoading(){
        binding.teacherHomeProgressBar.visibility= View.GONE
    }

    fun showLoading(){
        binding.teacherHomeProgressBar.visibility= View.VISIBLE
    }
    private  fun  setupRecycleView(){

        projectAdapter = ProjectAdapter()

        binding.projectRV.apply {
            adapter = projectAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

}