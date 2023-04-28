package com.mkrlabs.pmisdefence.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mkrlabs.pmisdefence.model.Project
import com.mkrlabs.pmisdefence.model.TaskItem
import com.mkrlabs.pmisdefence.repository.ProjectRepository
import com.mkrlabs.pmisdefence.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(val repository: ProjectRepository, val mAuth : FirebaseAuth) :ViewModel() {

    var createProjectState : MutableLiveData<Resource<Pair<Project,String>>> = MutableLiveData()
    var projectList : MutableLiveData<Resource<List<Project>>> = MutableLiveData()
    var taskItemList : MutableLiveData<Resource<List<TaskItem>>> = MutableLiveData()
    var createTaskItemState : MutableLiveData<Resource<String>> = MutableLiveData()
    var deleteTaskItemState : MutableLiveData<Resource<String>> = MutableLiveData()
    var editTaskItemState : MutableLiveData<Resource<String>> = MutableLiveData()




    fun createProject(project: Project){
        createProjectState.value = Resource.Loading()

        viewModelScope.launch{
            repository.createProject(project){
                createProjectState.postValue(it)
            }
        }

    }


    fun  createNewTask(projectId : String ,taskItem: TaskItem){
        createTaskItemState.postValue(Resource.Loading())

        viewModelScope.launch {
            repository.addTaskToProject(projectId,taskItem){
                createTaskItemState.postValue(it)
            }
        }

    }
    fun  deleteTask(projectId : String ,taskItem: TaskItem){
        deleteTaskItemState.postValue(Resource.Loading())

        viewModelScope.launch {
            repository.deleteTaskFromProject(projectId,taskItem.id){
                deleteTaskItemState.postValue(it)
            }
        }

    }
    fun  editTask(projectId : String ,taskItem: TaskItem){
        editTaskItemState.postValue(Resource.Loading())

        viewModelScope.launch {
            repository.editTaskFromProject(projectId,taskItem){
                editTaskItemState.postValue(it)
            }
        }

    }

    fun  fetchProjectList(){
        projectList.postValue(Resource.Loading())
        viewModelScope.launch {
            mAuth.currentUser?.let {
                repository.getAllProjectUnderTeacher(it.uid){
                    projectList.postValue(it)
                }

            }
        }
    }

    fun  fetchTaskList(projectId: String){
        taskItemList.postValue(Resource.Loading())
        viewModelScope.launch {
                repository.getAllTaskListOfAProject(projectId){
                    taskItemList.postValue(it)
                }


        }
    }



}