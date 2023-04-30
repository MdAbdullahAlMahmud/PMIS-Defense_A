package com.mkrlabs.pmisdefence.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mkrlabs.pmisdefence.model.ChatMessage
import com.mkrlabs.pmisdefence.model.Project
import com.mkrlabs.pmisdefence.util.CommonFunction
import com.mkrlabs.pmisdefence.util.Constant
import com.mkrlabs.pmisdefence.util.Resource
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

class ChatRepository @Inject constructor(val firebaseFirestore: FirebaseFirestore,
                      val mAuth: FirebaseAuth
){



    suspend fun  sendMessage(chatUID : String ,message: ChatMessage, result: (Resource<ChatMessage>) -> Unit){

        val uniqueMessageId = firebaseFirestore.collection(Constant.CHAT_NODE).document().id
        firebaseFirestore.collection(Constant.CHAT_NODE)
            .document(chatUID)
            .collection(Constant.MESSAGE_NODE)
            .document(uniqueMessageId)
            .set(message)
            .addOnSuccessListener {
                result.invoke(Resource.Success(message))
            }.addOnFailureListener {

                result.invoke(Resource.Error(it.localizedMessage.toString()))
            }


    }

}