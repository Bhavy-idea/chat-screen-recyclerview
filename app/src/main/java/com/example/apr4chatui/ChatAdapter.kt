package com.example.apr4chatui

import android.view.*
import androidx.core.net.*
import androidx.core.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.apr4chatui.databinding.LayoutLeftSideChatBinding
import com.example.apr4chatui.databinding.LayoutRightSideChatBinding

class ChatAdapter(private var chatList: ArrayList<ChatItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var SENDER = 1
    private var RECEIVER = 2

    inner class ReceiverViewHolder(private var view: LayoutLeftSideChatBinding) : RecyclerView.ViewHolder(view.root) {

        fun bindText(chatItem: ChatItem) {
            view.imageViewUserReceiveImage.isVisible = false
            view.textViewReceiverMessage.isVisible = true
            view.textViewReceiverMessage.text = chatItem.message
        }
        fun bindImage(chatItem: ChatItem) {
            view.textViewReceiverMessage.isVisible = false
            view.imageViewUserReceiveImage.isVisible = true
            view.imageViewUserReceiveImage.setImageURI(chatItem.message.toUri())
        }
    }

    inner class SenderViewHolder(private var view: LayoutRightSideChatBinding) : RecyclerView.ViewHolder(view.root) {

        fun bindText(chatItem: ChatItem) {
            view.imageViewUserReceiveImage.isVisible = false
            view.textViewSenderMessage.text = chatItem.message
        }
        fun bindImage(chatItem: ChatItem) {
            view.textViewSenderMessage.isVisible = false
            view.imageViewUserReceiveImage.setImageURI(chatItem.message.toUri())
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SENDER -> {
                SenderViewHolder(LayoutRightSideChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else -> {
                ReceiverViewHolder(LayoutLeftSideChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (chatList[position].isSender) {
            true -> RECEIVER
            else -> SENDER
        }
    }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == SENDER) {
            if (chatList[position].mediaType == 1){
                (holder as SenderViewHolder).bindImage(chatList[position])
            }else{
                (holder as SenderViewHolder).bindText(chatList[position])
            }
        } else {
            if (chatList[position].mediaType == 1){
                (holder as ReceiverViewHolder).bindImage(chatList[position])
            }else{
                (holder as ReceiverViewHolder).bindText(chatList[position])
            }
        }
    }
}