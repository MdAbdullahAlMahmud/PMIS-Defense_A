package com.mkrlabs.pmisdefence.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mkrlabs.pmisdefence.model.ChatMessage
import com.mkrlabs.pmisdefence.repository.ChatRepository
import com.mkrlabs.pmisdefence.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(val repository: ChatRepository , val mAuth : FirebaseAuth ):ViewModel(){


    var sendMessageState : MutableLiveData<Resource<ChatMessage>> = MutableLiveData()



    fun sendMessage( hisUID : String , chatMessage: ChatMessage){
        val mineUID = mAuth.currentUser?.uid
        val  chatUID = "${mineUID}_${hisUID}"
        sendMessageState.postValue(Resource.Loading())
        viewModelScope.launch{
            repository.sendMessage(chatUID,chatMessage){
                sendMessageState.postValue(it)
            }
        }
    }





}