package com.mkrlabs.pmisdefence.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mkrlabs.pmisdefence.model.ChatItem
import com.mkrlabs.pmisdefence.model.ChatMessage
import com.mkrlabs.pmisdefence.model.Project
import com.mkrlabs.pmisdefence.util.CommonFunction
import com.mkrlabs.pmisdefence.util.Constant
import com.mkrlabs.pmisdefence.util.Resource
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

class ChatRepository @Inject constructor(val firebaseFirestore: FirebaseFirestore, val mAuth: FirebaseAuth
){


    suspend fun chatUserList(result: (Resource<List<ChatItem>>) -> Unit){
        firebaseFirestore.collection(Constant.USER_NODE)
            .get().addOnSuccessListener {
                val userList = arrayListOf<ChatItem>()
                for (document in it) {
                    val user = document.toObject(ChatItem::class.java)

                    if (user.uid != mAuth.currentUser?.uid){
                        userList.add(user)
                    }

                    result.invoke(Resource.Success(userList))
                }
            }.addOnFailureListener {
                result.invoke(Resource.Error(it.localizedMessage.toString()))
            }

    }

    suspend fun  sendMessage(mineChatUID : String , hisChatUID:String ,message: ChatMessage, result: (Resource<ChatMessage>) -> Unit){

        val uniqueMessageId = firebaseFirestore.collection(Constant.CHAT_NODE).document().id

        message.messageId = uniqueMessageId

        firebaseFirestore.collection(Constant.CHAT_NODE)
            .document(mineChatUID)
            .collection(Constant.MESSAGE_NODE)
            .document(uniqueMessageId)
            .set(message)
            .addOnSuccessListener {
                firebaseFirestore.collection(Constant.CHAT_NODE)
                    .document(hisChatUID)
                    .collection(Constant.MESSAGE_NODE)
                    .document(uniqueMessageId)
                    .set(message).addOnSuccessListener {
                        result.invoke(Resource.Success(message))
                    }.addOnFailureListener {
                        result.invoke(Resource.Error(it.localizedMessage.toString()))
                    }

            }.addOnFailureListener {
                result.invoke(Resource.Error(it.localizedMessage.toString()))
            }

    }

    suspend fun messageList(mineChatUID: String, result: (Resource<List<ChatMessage>>) -> Unit){


        firebaseFirestore.collection(Constant.CHAT_NODE)
            .document(mineChatUID)
            .collection(Constant.MESSAGE_NODE)
            .get()
            .addOnSuccessListener {
                val messageList = arrayListOf<ChatMessage>()
                for (document in it) {
                    val message = document.toObject(ChatMessage::class.java)
                    messageList.add(message)
                }
                result.invoke(
                    Resource.Success(messageList)
                )
            }.addOnFailureListener {
                result.invoke(Resource.Error(it.localizedMessage.toString()))
            }



    }
}