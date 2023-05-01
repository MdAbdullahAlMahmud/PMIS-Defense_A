package com.mkrlabs.pmisdefence.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.adapter.ProjectAdapter
import com.mkrlabs.pmisdefence.databinding.FragmentHomeBinding
import com.mkrlabs.pmisdefence.util.CommonFunction
import com.mkrlabs.pmisdefence.util.Resource
import com.mkrlabs.pmisdefence.view_model.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    lateinit var mAuth : FirebaseAuth
    lateinit var projectViewModel: ProjectViewModel

    lateinit var projectAdapter: ProjectAdapter

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
        mAuth  = FirebaseAuth.getInstance()

        binding.logoutButton.setOnClickListener {
            mAuth.signOut()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }


        binding.addNewTeamHome.setOnClickListener{

            findNavController().navigate(R.id.action_homeFragment_to_projectInformationFragment)
        }



        projectViewModel.fetchProjectList()
        projectViewModel.projectList.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideLoading()
                    response.data?.let {
                        projectAdapter.differ.submitList(it)
                    }
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
            val bundle = Bundle().apply {
                putSerializable("project",projectIntent)
            }
        findNavController().navigate(R.id.action_homeFragment_to_projectDetailsFragment,bundle)

        }


        projectAdapter.setOnGroupMessageClickListener {

        }








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