package com.mkrlabs.pmisdefence.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.mkrlabs.pmisdefence.model.Project
import com.mkrlabs.pmisdefence.util.Constant
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProjectRepository @Inject constructor(val  firebaseFirestore: FirebaseFirestore){




    suspend fun  createProject(project: Project){


        val uniqueProjectUID = firebaseFirestore.collection(Constant.PROJECT_NODE).document().id

        project.projectUID = uniqueProjectUID


        //Database push

       // firebaseFirestore.collection(Constant.PROJECT_NODE).document(uniqueProjectUID).set(project).await()


    }

}