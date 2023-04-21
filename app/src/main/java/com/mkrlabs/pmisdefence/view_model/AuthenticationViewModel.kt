package com.mkrlabs.pmisdefence.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mkrlabs.pmisdefence.model.Teacher
import com.mkrlabs.pmisdefence.repository.AuthRepository
import com.mkrlabs.pmisdefence.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor (val authRepository: AuthRepository) : ViewModel() {

    val authState : MutableLiveData<Resource<FirebaseUser>> = MutableLiveData()
    val loginState : MutableLiveData<Resource<FirebaseUser>> = MutableLiveData()

    val savedUserData : MutableLiveData<Resource<String>> = MutableLiveData()


    fun createUserAccount( teacher :Teacher) {
        authState.postValue(Resource.Loading())
        viewModelScope.launch {
           val firebaseUser =  authRepository.crateUserAccount(teacher).data
            if (firebaseUser!=null){

                authState.postValue(Resource.Success(firebaseUser))
                teacher.uid = firebaseUser.uid
                saveUserData(teacher)
            }else{
                authState.postValue(Resource.Error("Something went wrong"))
            }
        }

    }


    fun  loginUser(email:String, password :String){
        loginState.postValue(Resource.Loading())
        viewModelScope.launch {
            val firebaseUser =  authRepository.loginUserWithEmailAndPassword(email,password).data
            if (firebaseUser!=null){
                loginState.postValue(Resource.Success(firebaseUser))
            }else{
                loginState.postValue(Resource.Error("Something went wrong"))
            }
        }

    }


    fun saveUserData(teacher: Teacher){
        savedUserData.postValue(Resource.Loading())
        viewModelScope.launch {

            val response = authRepository.saveUserAccount(teacher)
            savedUserData.postValue(Resource.Success("Data Saved successfully"))

        }
    }

}