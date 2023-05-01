package com.mkrlabs.pmisdefence.fragment.project_details_tab

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.set
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.adapter.MessageAdapter
import com.mkrlabs.pmisdefence.databinding.FragmentChatBinding
import com.mkrlabs.pmisdefence.databinding.FragmentOverviewTabBinding
import com.mkrlabs.pmisdefence.model.ChatMessage
import com.mkrlabs.pmisdefence.model.LayoutType
import com.mkrlabs.pmisdefence.model.Message
import com.mkrlabs.pmisdefence.model.MessageType
import com.mkrlabs.pmisdefence.util.CommonFunction
import com.mkrlabs.pmisdefence.util.Resource
import com.mkrlabs.pmisdefence.view_model.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding

    lateinit var adapter :MessageAdapter

    lateinit var chatMessageList : MutableList<Message>

    lateinit var chatViewModel: ChatViewModel

    val  chatItem : ChatFragmentArgs by navArgs()
    val hisUID = "0VaSwE3jJ6c6EKZ3DhAWYVinZat2"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MessageAdapter()

        initRecycleView(view.context)
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        chatMessageList = mutableListOf<Message>()


        CommonFunction.infoToast(view.context,"${chatItem.chatItem.uid}")

        chatViewModel.messageListMine(hisUID)

        binding.sendButton.setOnClickListener {


            if (binding.messageInput.text.toString().isEmpty()){

                Toast.makeText(view.context,"Please write something !!", Toast.LENGTH_SHORT).show()
            }else{
                /*val userMessage = Message(binding.messageInput.text.toString(),MessageType.TEXT,LayoutType.SENDER,12323L,0)
                adapter.addMessage(userMessage)

                val responseMessage = Message("I am getting your message",MessageType.TEXT,LayoutType.RECEIVER,12323L,0)
                adapter.addMessage(responseMessage)

                binding.messageInput.setText("")*/

                val userMessage = binding.messageInput.text.toString()
                val chatMessage = ChatMessage(userMessage,"",CommonFunction.loggedInUserUID(),Date().time,MessageType.TEXT)
                chatViewModel.sendMessage(hisUID,chatMessage)
                binding.messageInput.setText("")
                chatViewModel.mineMessageState.postValue(Resource.Loading())
                chatViewModel.messageListMine(hisUID)
            }
        }


        chatViewModel.sendMessageState.observe(viewLifecycleOwner, Observer {response->


            when(response){
                is  Resource.Success ->{


                    var layoutType : LayoutType? = null
                    response.data?.let {

                        if (it.senderId.equals(CommonFunction.loggedInUserUID())){
                            layoutType = LayoutType.SENDER
                        }else{
                            layoutType = LayoutType.RECEIVER
                        }
                        val responseMessage = Message(it.message,MessageType.TEXT, layoutType!!,it.timestamp,0)
                        adapter.addMessage(responseMessage)
                        binding.recyclerView.smoothScrollToPosition(adapter.itemCount)

                    }

                }
                is  Resource.Error ->{
                    CommonFunction.errorToast(view.context,response.message.toString())

                }
                is  Resource.Loading->{

                }
            }





        })
        chatViewModel.mineMessageState.observe(viewLifecycleOwner, Observer {response->


            when(response){
                is  Resource.Success ->{

                    var layoutType : LayoutType? = null
                    response.data?.let {messageList->

                       messageList.forEach {
                           if (it.senderId.equals(CommonFunction.loggedInUserUID())){
                               layoutType = LayoutType.SENDER
                           }else{
                               layoutType = LayoutType.RECEIVER
                           }
                           val responseMessage = Message(it.message,MessageType.TEXT, layoutType!!,it.timestamp,0)
                           adapter.addMessage(responseMessage)
                           binding.recyclerView.smoothScrollToPosition(adapter.itemCount)
                       }



                    }

                }
                is  Resource.Error ->{
                    CommonFunction.errorToast(view.context,response.message.toString())

                }
                is  Resource.Loading->{

                }
            }





        })




    }

    fun  initRecycleView(context: Context){
        binding.recyclerView.adapter = adapter
        binding.recyclerView.apply {
            layoutManager= LinearLayoutManager(context)
            adapter = adapter
            adapter?.let { smoothScrollToPosition(it.itemCount) }
        }
    }

}