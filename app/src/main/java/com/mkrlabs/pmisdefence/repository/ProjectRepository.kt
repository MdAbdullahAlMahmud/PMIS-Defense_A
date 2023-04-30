package com.mkrlabs.pmisdefence.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mkrlabs.pmisdefence.model.Project
import com.mkrlabs.pmisdefence.model.TaskItem
import com.mkrlabs.pmisdefence.util.Constant
import com.mkrlabs.pmisdefence.util.Resource
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

class ProjectRepository @Inject constructor(
    val firebaseFirestore: FirebaseFirestore,
    val mAuth: FirebaseAuth
) {




    suspend fun createProject(
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


    suspend fun  addTaskToProject(projectId : String , task: TaskItem,
                                  result: (Resource<String>) -> Unit
    ){
        val taskUniqueId = firebaseFirestore.collection(Constant.PROJECT_NODE).document(projectId).collection(Constant.TASK_NODE).document().id

        task.id = taskUniqueId
        firebaseFirestore.collection(Constant.PROJECT_NODE)
            .document(projectId)
            .collection(Constant.TASK_NODE)
            .document(taskUniqueId)
            .set(task).addOnSuccessListener {
                result.invoke(
                    Resource.Success("Task Added Successfully")
                )
            }.addOnFailureListener {
            result.invoke(
                Resource.Error(it.localizedMessage.toString())
            )

            }
    }


    suspend fun deleteTaskFromProject(projectId: String,taskId : String, result: (Resource<String>) -> Unit){
        firebaseFirestore.collection(Constant.PROJECT_NODE)
            .document(projectId)
            .collection(Constant.TASK_NODE)
            .document(taskId)
            .delete().addOnSuccessListener {
                result.invoke(Resource.Success("Task Removed Successfully"))

            }.addOnFailureListener {
                result.invoke(Resource.Error(it.localizedMessage.toString()))
            }
    }

     suspend fun editTaskFromProject(projectId: String,task: TaskItem, result: (Resource<String>) -> Unit){

        firebaseFirestore.collection(Constant.PROJECT_NODE)
            .document(projectId)
            .collection(Constant.TASK_NODE)
            .document(task.id)
            .set(task)
            .addOnSuccessListener {
                result.invoke(Resource.Success("Task Edit Successfully"))

            }.addOnFailureListener {
                result.invoke(Resource.Error(it.localizedMessage.toString()))
            }
    }



    suspend fun getAllTaskListOfAProject(
        projectId:String,
        result: (Resource<List<TaskItem>>) ->Unit
    ){



        firebaseFirestore.collection(Constant.PROJECT_NODE)
            .document(projectId)
            .collection(Constant.TASK_NODE)
            .orderBy("timestamp",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                val taskItems = arrayListOf<TaskItem>()
                for (document in it) {
                    val taskItem = document.toObject(TaskItem::class.java)
                    taskItems.add(taskItem)
                }
                result.invoke(
                    Resource.Success(taskItems)
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


    suspend fun getAllTaskOverviewOfAProject(
        projectId:String,
        result: (Resource<Pair<Int,Int>>) ->Unit
    ){



        firebaseFirestore.collection(Constant.PROJECT_NODE)
            .document(projectId)
            .collection(Constant.TASK_NODE)
            .orderBy("timestamp",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                val taskItems = arrayListOf<TaskItem>()
                var countCompletedTask = 0
                for (document in it) {
                    val taskItem = document.toObject(TaskItem::class.java)

                    if (taskItem.status)countCompletedTask++
                    taskItems.add(taskItem)
                }
                result.invoke(
                    Resource.Success(Pair(taskItems.size,countCompletedTask))
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