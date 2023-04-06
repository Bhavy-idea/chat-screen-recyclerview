package com.example.apr4chatui

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.*
import android.widget.*
import androidx.activity.result.contract.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apr4chatui.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var imageUri = ""
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

        val imageData = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->

            it.data?.data?.let {
                imageUri = it.toString()
                if (imageUri.toString().isNotEmpty()) {
                    Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val manager = LinearLayoutManager(this)
        binding.recyclerViewChat.layoutManager = manager
        binding.recyclerViewChat.adapter = chatAdapter

        binding.isSender.setOnCheckedChangeListener { buttonView, isChecked ->
            isSender = isChecked
        }

        binding.recyclerViewChat.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

//            Log.d("stateChange View", v.toString())
//            Log.d("stateChange Scroll X", scrollX.toString())
//            Log.d("stateChange Scroll Y", scrollY.toString())

//            Log.d("data",manager.findFirstCompletelyVisibleItemPosition().toString())
//            Log.d("data",manager.findFirstVisibleItemPosition().toString())
//            Log.d("data",manager.findLastCompletelyVisibleItemPosition().toString())
//            Log.d("data",manager.findLastVisibleItemPosition().toString())

        }

        binding.imageViewUser.setOnClickListener {
            mediaType = 1
            val gallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            imageData.launch(gallery)
        }

        binding.imageViewSendMessage.setOnClickListener {

            val editTextMessage = binding.editTextMessage.text.toString()

            if (editTextMessage.isEmpty()) {
                if (imageUri.toString().isNotBlank()){
                    chatItemList.add(ChatItem(isSender, imageUri.toString(), mediaType))
                    chatAdapter.notifyItemInserted(chatItemList.size)
                    imageUri = ""
                    mediaType = -1
                }else{
                    Toast.makeText(this, "Please Enter Message or Image", Toast.LENGTH_SHORT).show()
                }
            } else {
                mediaType = 2
                if (editTextMessage.isNotEmpty()) {
                    chatItemList.add(ChatItem(isSender, binding.editTextMessage.text.toString(), mediaType))
                    chatAdapter.notifyItemInserted(chatItemList.size)
                    mediaType = -1
                    binding.editTextMessage.text?.clear()
                } else {
                    Toast.makeText(this, "Please Enter Message or Image", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}