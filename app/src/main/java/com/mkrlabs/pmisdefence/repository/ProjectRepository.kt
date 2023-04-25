package com.mkrlabs.pmisdefence.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mkrlabs.pmisdefence.model.Project
import com.mkrlabs.pmisdefence.util.Constant
import com.mkrlabs.pmisdefence.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProjectRepository @Inject constructor(
    val firebaseFirestore: FirebaseFirestore,
    val mAuth: FirebaseAuth
) {


    /* suspend fun  createProject(project: Project) :Resource<Pair<String,Boolean>>{

         val response = MutableLiveData<Resource<Pair<String,Boolean>>>()

         val uniqueProjectUID = firebaseFirestore.collection(Constant.PROJECT_NODE).document().id

         project.projectUID = uniqueProjectUID

       firebaseFirestore.collection(Constant.PROJECT_NODE).document(uniqueProjectUID).set(project).addOnCompleteListener {

           if (it.isSuccessful){
               response.postValue(Resource.Success(Pair("Create project successfully",true)))
           }else{
               response.postValue(Resource.Error(it.exception?.localizedMessage.toString()))

           }
        }



     }*/


    suspend fun createProjectV2(project: Project): Task<Void> {

        val uniqueProjectUID = firebaseFirestore.collection(Constant.PROJECT_NODE).document().id
        project.projectUID = uniqueProjectUID

        return firebaseFirestore.collection(Constant.PROJECT_NODE).document(uniqueProjectUID)
            .set(project)
    }

    suspend fun createProjectV3(
        project: Project,
        result: (Resource<Pair<Project, String>>) -> Unit
    ) {

        val uniqueProjectUID = firebaseFirestore.collection(Constant.PROJECT_NODE).document().id
        project.projectUID = uniqueProjectUID
        mAuth.currentUser?.let {
            project.teacher_id = it.uid
        }

        firebaseFirestore.collection(Constant.PROJECT_NODE).document(uniqueProjectUID).set(project)
            .addOnSuccessListener {

                result.invoke(
                    Resource.Success(Pair(project, "Project created successfully"))
                )
            }.addOnFailureListener {

            result.invoke(
                Resource.Error(it.localizedMessage.toString())
            )
        }
    }


    suspend fun getAllProjectUnderTeacher(
        teacher_uid:String,
        result: (Resource<List<Project>>) ->Unit
    ){



        firebaseFirestore.collection(Constant.PROJECT_NODE)
            .whereEqualTo("teacher_id",teacher_uid)
            .get()
            .addOnSuccessListener {
                val projects = arrayListOf<Project>()
                for (document in it) {
                    val project = document.toObject(Project::class.java)
                    projects.add(project)
                }
                result.invoke(
                    Resource.Success(projects)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    Resource.Error(
                        it.localizedMessage
                    )
                )
            }

    }


}