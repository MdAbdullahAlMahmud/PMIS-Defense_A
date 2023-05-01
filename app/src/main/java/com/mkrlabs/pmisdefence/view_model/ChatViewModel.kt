package com.mkrlabs.pmisdefence.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mkrlabs.pmisdefence.model.ChatItem
import com.mkrlabs.pmisdefence.model.ChatMessage
import com.mkrlabs.pmisdefence.repository.ChatRepository
import com.mkrlabs.pmisdefence.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(val repository: ChatRepository , val mAuth : FirebaseAuth ):ViewModel(){


    var sendMessageState : MutableLiveData<Resource<ChatMessage>> = MutableLiveData()
    var mineMessageState : MutableLiveData<Resource<List<ChatMessage>>> = MutableLiveData()
    var chatUserListState : MutableLiveData<Resource<List<ChatItem>>> = MutableLiveData()



    fun sendMessage( hisUID : String , chatMessage: ChatMessage){
        val mineUID = mAuth.currentUser?.uid
        val  mineChatUID = "${mineUID}_${hisUID}"
        val  hisChatUID = "${hisUID}_${mineUID}"

        sendMessageState.postValue(Resource.Loading())
        viewModelScope.launch{
            repository.sendMessage(mineChatUID,hisChatUID,chatMessage){
                sendMessageState.postValue(it)
            }
        }
    }



    fun messageListMine(hisUID : String){
     val mineUID = mAuth.currentUser?.uid
     val  mineChatUID = "${mineUID}_${hisUID}"

        mineMessageState.postValue(Resource.Loading())
        viewModelScope.launch {
            repository.messageList(mineChatUID){
                mineMessageState.postValue(it)
            }
        }
    }

    fun  chatUserList(){
        val loggegInUserId = mAuth.currentUser?.uid
        chatUserListState.postValue(Resource.Loading())

        viewModelScope.launch {
            repository.chatUserList {
                chatUserListState.postValue(it)
            }
        }
    }




}