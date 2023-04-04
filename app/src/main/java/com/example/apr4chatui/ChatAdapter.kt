package com.example.apr4chatui

import android.view.*
import androidx.core.net.*
import androidx.recyclerview.widget.RecyclerView
import com.example.apr4chatui.databinding.LayoutChatReceiverImageBinding
import com.example.apr4chatui.databinding.LayoutChatSenderImageBinding
import com.example.apr4chatui.databinding.LayoutLeftSideChatBinding
import com.example.apr4chatui.databinding.LayoutRightSideChatBinding

class ChatAdapter(private var chatList : ArrayList<ChatItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var SENDER = 1
    private var RECEIVER = 2
    var positionOfItem = -1

    inner class SenderImageViewHolder(private var view : LayoutChatSenderImageBinding) : RecyclerView.ViewHolder(view.root){
        fun bind(chatItem: ChatItem){
            view.imageViewUseSendImage.setImageURI(chatItem.message.toUri())
        }
    }

    inner class ReceiverImageViewHolder(private var view : LayoutChatReceiverImageBinding) : RecyclerView.ViewHolder(view.root){
        fun bind(chatItem: ChatItem){
            view.imageViewUserReceiveImage.setImageURI(chatItem.message.toUri())
        }
    }

    inner class SenderViewHolder(private var view : LayoutRightSideChatBinding) : RecyclerView.ViewHolder(view.root){
        fun bind(chatItem: ChatItem){
            view.textViewSenderMessage.text = chatItem.message
        }
    }

    inner class ReceiverViewHolder(private var view : LayoutLeftSideChatBinding) : RecyclerView.ViewHolder(view.root){
        fun bind(chatItem: ChatItem){
            view.textViewReceiverMessage.text = chatItem.message
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            SENDER -> {

                if (chatList[positionOfItem].mediaType == 1){
                    SenderImageViewHolder(LayoutChatSenderImageBinding.inflate(LayoutInflater.from(parent.context),parent,false))
                }else {
                    SenderViewHolder(LayoutRightSideChatBinding.inflate(LayoutInflater.from(parent.context),parent,false))
                }
            }
            else -> {

                if (chatList[positionOfItem].mediaType == 1){
                    ReceiverImageViewHolder(LayoutChatReceiverImageBinding.inflate(LayoutInflater.from(parent.context),parent,false))
                }else{
                    ReceiverViewHolder(LayoutLeftSideChatBinding.inflate(LayoutInflater.from(parent.context),parent,false))
                }

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        positionOfItem = position
        return when(chatList[position].isSender){
            true -> RECEIVER
            else -> SENDER
        }
    }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == SENDER){

            if (chatList[position].mediaType == 1){
                (holder as SenderImageViewHolder).bind(chatList[position])
            }else{
                (holder as SenderViewHolder).bind(chatList[position])
            }

        }else{
            if (chatList[position].mediaType == 1){
                (holder as ReceiverImageViewHolder).bind(chatList[position])
            }else{
                (holder as ReceiverViewHolder).bind(chatList[position])
            }
        }
    }
}