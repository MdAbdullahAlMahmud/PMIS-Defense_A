package com.mkrlabs.pmisdefence.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mkrlabs.pmisdefence.R
import com.mkrlabs.pmisdefence.model.LayoutType
import com.mkrlabs.pmisdefence.model.Message
import com.mkrlabs.pmisdefence.model.MessageType


class MessageAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_SENT = 1
    val ITEM_RECEIVE = 2
    private val messageList = mutableListOf<Message>()




    fun addMessage(message: Message) {
        messageList.add(message)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        return if (viewType == ITEM_SENT) {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.message_send, parent, false)
            SenderViewHolder(view)
        } else {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.message_receive, parent, false)
            ReceiverViewHolder(view)
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message: Message = messageList[position]


       //Log.v("Holder", "Holder Name ->>>>>> ${holder.itemViewType}")

        if ((holder.itemViewType -1) == LayoutType.SENDER.ordinal){
            Log.v("Holder", "Holder Name ->>>>>> Sender")
            val senderViewHolder = holder as SenderViewHolder
            senderViewHolder.sender_message_item.text= message.message

        }else{
            Log.v("Holder", "Holder Name ->>>>>> Receiver")
            val receiverViewHolder = holder as ReceiverViewHolder
            if (message.messageType == MessageType.IMAGE) {
                Log.v("Image", "Image Layout Found")
                Log.v("MSG", "Adapter -> " + message.toString())
                receiverViewHolder.receiverMessageImageLayout.visibility = View.VISIBLE
                receiverViewHolder.receiverMessageTextLayout.visibility = View.GONE
                if (message.message.isEmpty()) {
                    receiverViewHolder.receive_message_image_text.visibility = View.GONE
                } else {
                    receiverViewHolder.receive_message_image_text.visibility = View.VISIBLE
                    receiverViewHolder.receive_message_image_text.setText(message.message)
                }
                receiverViewHolder.receive_message_image.setImageResource(message.image)
            } else {
                receiverViewHolder.receiverMessageImageLayout.visibility = View.GONE
                receiverViewHolder.receiverMessageTextLayout.visibility = View.VISIBLE
                receiverViewHolder.receive_message_item.setText(message.message)
            }
        }


    }

    override fun getItemViewType(position: Int): Int {
        val message: Message = messageList[position]
        return if (message.layoutType === LayoutType.SENDER) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    inner class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sender_message_item: TextView

        init {
            sender_message_item = itemView.findViewById(R.id.sender_message_item)
        }
    }

    inner class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var receive_message_item: TextView
        var receive_message_image_text: TextView
        var receiverMessageImageLayout: LinearLayout
        var receiverMessageTextLayout: LinearLayout
        var receive_message_image: ImageView

        init {
            receive_message_item = itemView.findViewById(R.id.receive_message_item)
            receiverMessageImageLayout = itemView.findViewById(R.id.receiverMessageImageLayout)
            receiverMessageTextLayout = itemView.findViewById(R.id.receiverMessageTextLayout)
            receive_message_image = itemView.findViewById(R.id.receive_message_image)
            receive_message_image_text = itemView.findViewById(R.id.receive_message_image_text)
        }
    }
}