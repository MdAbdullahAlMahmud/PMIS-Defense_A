package com.mkrlabs.pmisdefence.fragment.project_details_tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.set
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.adapter.MessageAdapter
import com.mkrlabs.pmisdefence.databinding.FragmentChatBinding
import com.mkrlabs.pmisdefence.databinding.FragmentOverviewTabBinding
import com.mkrlabs.pmisdefence.model.LayoutType
import com.mkrlabs.pmisdefence.model.Message
import com.mkrlabs.pmisdefence.model.MessageType


class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding

    lateinit var adapter :MessageAdapter

    lateinit var chatMessageList : MutableList<Message>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatMessageList = mutableListOf<Message>()





        adapter = MessageAdapter()
        binding.sendButton.setOnClickListener {


            if (binding.messageInput.text.toString().isEmpty()){

                Toast.makeText(view.context,"Please write something !!", Toast.LENGTH_SHORT).show()
            }else{
                val userMessage = Message(binding.messageInput.text.toString(),MessageType.TEXT,LayoutType.SENDER,12323L,0)
                adapter.addMessage(userMessage)

                val responseMessage = Message("I am getting your message",MessageType.TEXT,LayoutType.RECEIVER,12323L,0)
                adapter.addMessage(responseMessage)

                binding.messageInput.setText("")

            }
            binding.recyclerView.smoothScrollToPosition(adapter.itemCount)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.apply {
            layoutManager= LinearLayoutManager(view.context)
            adapter = adapter
            adapter?.let { smoothScrollToPosition(it.itemCount) }
        }

    }


}