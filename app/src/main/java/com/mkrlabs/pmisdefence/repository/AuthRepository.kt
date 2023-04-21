package com.mkrlabs.pmisdefence.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.mkrlabs.pmisdefence.model.Teacher
import com.mkrlabs.pmisdefence.util.Constant
import com.mkrlabs.pmisdefence.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(val mAuth: FirebaseAuth, val  firebaseFirestore: FirebaseFirestore){



    suspend fun crateUserAccount(teacher: Teacher): Resource<FirebaseUser> {
        try {
            val result =  mAuth.createUserWithEmailAndPassword(teacher.email,teacher.password).await()
          return  Resource.Success(result.user!!)


        }catch (e :Exception){
            e.printStackTrace()
            return Resource.Error(e.localizedMessage,null)

        }

    }
    suspend fun loginUserWithEmailAndPassword(email:String, password :String): Resource<FirebaseUser> {
        try {
            val result =  mAuth.signInWithEmailAndPassword(email,password).await()
          return  Resource.Success(result.user!!)


        }catch (e :Exception){
            e.printStackTrace()
            return Resource.Error(e.localizedMessage,null)

        }

    }




    suspend fun saveUserAccount(teacher: Teacher)  = firebaseFirestore.collection(Constant.USER_NODE).document(teacher.uid).set(teacher).await()





}