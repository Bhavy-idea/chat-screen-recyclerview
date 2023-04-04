package com.example.apr4chatui

import android.content.*
import android.net.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.*
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apr4chatui.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var imageUri: Uri
    private lateinit var binding: ActivityMainBinding
    private lateinit var chatAdapter: ChatAdapter
    private var chatItemList = ArrayList<ChatItem>()
    var isSender = false
    var mediaType = - 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        chatAdapter = ChatAdapter(chatItemList)

        binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChat.adapter = chatAdapter

        binding.isSender.setOnCheckedChangeListener { buttonView, isChecked ->
            isSender = isChecked
        }

        binding.imageViewUser.setOnClickListener {
            mediaType = 1
            val gallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)

        }

        binding.imageViewSendMessage.setOnClickListener {

            val editTextMessage = binding.editTextMessage.text.toString()

            if (editTextMessage.isEmpty()) {
                if (mediaType != 1) {
                    mediaType = 2
                    if (binding.editTextMessage.text.toString().isNotEmpty()) {
                        chatItemList.add(ChatItem(isSender, editTextMessage, mediaType))
                        chatAdapter.notifyItemInserted(chatItemList.size)
                        binding.editTextMessage.text?.clear()
                    } else {
                        Toast.makeText(this, "Please Enter Message", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    chatItemList.add(ChatItem(isSender, imageUri.toString(), mediaType))
                    chatAdapter.notifyItemInserted(chatItemList.size)
                }
            }else{
                mediaType = 2
                chatItemList.add(ChatItem(isSender, editTextMessage, mediaType))
                chatAdapter.notifyItemInserted(chatItemList.size)
                binding.editTextMessage.text?.clear()
            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {

            data?.data?.let {
                imageUri = it
                if (imageUri.toString().isNotEmpty()) {
                    Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}