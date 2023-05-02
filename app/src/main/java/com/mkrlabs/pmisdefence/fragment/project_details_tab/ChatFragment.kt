package com.mkrlabs.pmisdefence.fragment.project_details_tab

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mkrlabs.pmisdefence.adapter.MessageAdapter
import com.mkrlabs.pmisdefence.databinding.FragmentChatBinding
import com.mkrlabs.pmisdefence.model.Message
import com.mkrlabs.pmisdefence.model.MessageType
import com.mkrlabs.pmisdefence.util.CommonFunction
import com.mkrlabs.pmisdefence.util.Constant
import com.mkrlabs.pmisdefence.util.Constant.MESSAGE_NODE
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding

    lateinit var messageAdapter :MessageAdapter

    lateinit var chatMessageList : ArrayList<Message>

    lateinit var database : DatabaseReference



    val  chatItem : ChatFragmentArgs by navArgs()

    lateinit var CHAT_ROOM_MINE :String
    lateinit var CHAT_ROOM_HIS :String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        database = FirebaseDatabase.getInstance().reference

        chatMessageList = ArrayList()
        messageAdapter = MessageAdapter(chatMessageList)
        initRecycleView(view.context)








        binding.sendButton.setOnClickListener {


            if (binding.messageInput.text.toString().isEmpty()){

                Toast.makeText(view.context,"Please write something !!", Toast.LENGTH_SHORT).show()
            }else{

                var messageText = binding.messageInput.text.toString()
                val messageId = database.push().key.toString()

                var messageItem = Message(messageText,messageId,MessageType.TEXT,"",Date().time)

                CommonFunction.infoToast(view.context,"Before Send Message")
                sendMessage(view.context,messageItem)

            }
        }


/*

        database.reference
            .child(Constant.CHAT_NODE)
            .child(CHAT_ROOM_MINE)
            .child(MESSAGE_NODE)
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    chatMessageList.clear()
                    for (data in snapshot.children){
                        var message : Message? = data.getValue(Message::class.java)
                        if (message != null) {
                            chatMessageList.add(message)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                    CommonFunction.errorToast(view.context,"Error ${error.message}")
                }

            })


*/





    }
    fun init(){
        buildChatRoom()
    }

    fun sendMessage(context: Context,message: Message){

        CommonFunction.infoToast(context,"OnSend Message")



        database.child(Constant.CHAT_NODE)
            .child(CHAT_ROOM_MINE)
            .child(MESSAGE_NODE)
            .child(message.messageId)
            .setValue(message)
            .addOnSuccessListener {
                CommonFunction.successToast(context,"Mine Completed")

                database.child(Constant.CHAT_NODE)
                    .child(CHAT_ROOM_HIS)
                    .child(MESSAGE_NODE)
                    .child(message.messageId)
                    .setValue(message)
                    .addOnSuccessListener {
                        CommonFunction.successToast(context,"His Completed")

                        binding.messageInput.setText("")

                    }.addOnFailureListener {
                        CommonFunction.errorToast(context,"Message sent failed with error ${it.message}")
                        it.printStackTrace()
                    }

            }.addOnFailureListener {
                CommonFunction.errorToast(context,"Message sent failed with error ${it.message}")
                it.printStackTrace()
            }
    }
    private fun buildChatRoom(){

        val mineUID = FirebaseAuth.getInstance().uid
        val  hisUID = chatItem.chatItem.uid

        CHAT_ROOM_MINE = "${mineUID}_${hisUID}"
        CHAT_ROOM_HIS = "${hisUID}_${mineUID}"
    }
    fun  initRecycleView(context: Context){
        binding.recyclerView.apply {
            layoutManager= LinearLayoutManager(context)
            adapter =messageAdapter
            adapter?.let { smoothScrollToPosition(it.itemCount) }
        }
    }

}