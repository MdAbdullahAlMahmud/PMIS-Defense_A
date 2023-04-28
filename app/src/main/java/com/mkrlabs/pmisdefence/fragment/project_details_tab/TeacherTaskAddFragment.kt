package com.mkrlabs.pmisdefence.fragment.project_details_tab

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.adapter.ProjectAdapter
import com.mkrlabs.pmisdefence.adapter.TaskAdapter
import com.mkrlabs.pmisdefence.databinding.CreateTaskBottomSheetBinding
import com.mkrlabs.pmisdefence.databinding.FragmentTeacherTaskAddBinding
import com.mkrlabs.pmisdefence.fragment.ProjectDetailsFragment
import com.mkrlabs.pmisdefence.model.Project
import com.mkrlabs.pmisdefence.model.TaskItem
import com.mkrlabs.pmisdefence.util.CommonFunction
import com.mkrlabs.pmisdefence.util.Resource
import com.mkrlabs.pmisdefence.view_model.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class TeacherTaskAddFragment (val  projectId :String): Fragment() {

    lateinit var binding: FragmentTeacherTaskAddBinding
    lateinit var taskBinding : CreateTaskBottomSheetBinding

    lateinit var  projectViewModel: ProjectViewModel
    lateinit var  taskAdapter: TaskAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeacherTaskAddBinding.inflate(layoutInflater)
        return  binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        projectViewModel = ViewModelProvider(this)[ProjectViewModel::class.java]



        binding.teacherTabaddTaskButton.setOnClickListener {


            val inflater = LayoutInflater.from(requireContext())
              taskBinding = CreateTaskBottomSheetBinding.inflate(inflater)

            val dialog = BottomSheetDialog(view.context)

            dialog.setContentView(taskBinding.root)

            dialog.show()


            taskBinding.createTaskAddTaskButton.setOnClickListener {

                 val taskDescription = taskBinding.createTaskTaskDescriptionEdt.text.toString()
                 if (taskDescription.isEmpty()){
                     taskBinding.createTaskTaskDescriptionEdt.error = "requires"
                     return@setOnClickListener
                 }
                val  taskItem = TaskItem(taskDescription,"",Date().time,false)
                projectViewModel.createTaskItemState.postValue(Resource.Loading())
                projectViewModel.createNewTask(projectId,taskItem)

            }


            projectViewModel.createTaskItemState.observe(viewLifecycleOwner, Observer {response->

                when(response){

                    is Resource.Success->{
                        hideLoading()
                        response.data?.let {
                            CommonFunction.successToast(view.context,it)
                            dialog.dismiss()
                            fetchTaskList()
                        }
                    }

                    is Resource.Loading->{
                        showLoading()
                    }
                    is Resource.Error->{
                        hideLoading()
                        dialog.dismiss()
                        CommonFunction.successToast(view.context,response.message.toString())
                    }
                }
            })

        }


        fetchTaskList()


        taskAdapter.setOnTaskMenuItemClickListener {view,taskItem->

            val popupMenu = PopupMenu(context,view)
            popupMenu.menuInflater.inflate(R.menu.pop_up_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.taskPopUpMenuEditIcon ->{

                    }
                    R.id.taskPopUpMenuDeleteIcon ->{

                        projectViewModel.deleteTask(projectId,taskItem)
                        deleteTaskItem(view.context)

                    }

                }
                true
            })
            popupMenu.show()

        }

    }


    private fun deleteTaskItem(context: Context){
        projectViewModel.deleteTaskItemState.postValue(Resource.Loading())
        projectViewModel.deleteTaskItemState.observe(viewLifecycleOwner, Observer {response->

            when(response){

                is Resource.Success->{
                    response.data?.let {
                        fetchTaskList()
                        CommonFunction.successToast(context,it)

                    }
                }

                is Resource.Loading->{
                }
                is Resource.Error->{
                    CommonFunction.successToast(context,response.message.toString())
                }
            }
        })
    }
    fun hideLoading(){
        taskBinding.createTaskProgressBar.visibility= View.GONE
    }

    fun showLoading(){
        taskBinding.createTaskProgressBar.visibility= View.VISIBLE
    }

    fun hideTaskLoading(){
        binding.teacherAddTaskProgressBar.visibility= View.GONE
    }

    fun showTaskLoading(){
        binding.teacherAddTaskProgressBar.visibility= View.VISIBLE
    }



    private fun  init(){
        setupRecycleView()
    }

    private fun fetchTaskList(){
        projectViewModel.taskItemList.postValue(Resource.Loading())
        projectViewModel.fetchTaskList(projectId)
        projectViewModel.taskItemList.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideTaskLoading()
                    response.data?.let {
                        taskAdapter.differ.submitList(it)
                    }
                }
                is Resource.Loading->{
                    showTaskLoading()
                }
                is Resource.Error->{
                    hideTaskLoading()
                    context?.let { CommonFunction.successToast(it,response.message.toString()) }
                }
            }
        })
    }
    private  fun  setupRecycleView(){

        taskAdapter = TaskAdapter()

        binding.taskRV.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }


}