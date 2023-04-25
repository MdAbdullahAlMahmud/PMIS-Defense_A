package com.mkrlabs.pmisdefence.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mkrlabs.pmisdefence.model.Project
import com.mkrlabs.pmisdefence.repository.ProjectRepository
import com.mkrlabs.pmisdefence.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(val repository: ProjectRepository, val mAuth : FirebaseAuth) :ViewModel() {

    var createProjectState : MutableLiveData<Resource<Pair<String,String>>> = MutableLiveData()
    var createProjectStateV3 : MutableLiveData<Resource<Pair<Project,String>>> = MutableLiveData()
    var projectList : MutableLiveData<Resource<List<Project>>> = MutableLiveData()



    fun createProject (project: Project){

        createProjectState.postValue(Resource.Loading())

        viewModelScope.launch {

          repository.createProjectV2(project)

              .addOnSuccessListener {
              createProjectState.postValue(Resource.Success(Pair("","Project Created Successfully")))
          }.addOnFailureListener{
              createProjectState.postValue(Resource.Error(it.localizedMessage.toString()))
          }


        }

    }

    fun createProjectV3(project: Project){
        createProjectStateV3.value = Resource.Loading()

        viewModelScope.launch{
            repository.createProjectV3(project){
                createProjectStateV3.postValue(it)
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

}