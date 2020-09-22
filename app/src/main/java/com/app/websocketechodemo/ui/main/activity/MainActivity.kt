package com.app.websocketechodemo.ui.main.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.websocketechodemo.R
import com.app.websocketechodemo.model.MessageItemDataModel
import com.app.websocketechodemo.model.MessageType
import com.app.websocketechodemo.socket.WebSocketConnector
import com.app.websocketechodemo.ui.main.adapter.MessagesListAdapter
import com.app.websocketechodemo.utility.NetworkUtility
import com.app.websocketechodemo.utility.isNetworkAvailable
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.WebSocket
import okhttp3.WebSocketListener

private const val TAG = "MainActivity"
class MainActivity: AppCompatActivity() {
    private lateinit var messagesListAdapter: MessagesListAdapter
    private val dataList = mutableListOf<MessageItemDataModel>()

    /**
     * Listener for handling events related to Socket
     */
    private val webSocketListener = object: WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d(TAG, "onMessage response: $text")
            addData(message = text, messageType = MessageType.RECEIVED)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NetworkUtility(this, this) { networkAvailable ->
            if (networkAvailable) initConnection()
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.stackFromEnd = true
        messagesListAdapter = MessagesListAdapter()
        recycler_view_messages.layoutManager = layoutManager
        recycler_view_messages.adapter = messagesListAdapter
        messagesListAdapter.submitList(dataList)
        messagesListAdapter.notifyDataSetChanged()

        image_view_send_message.setOnClickListener {
            checkAndSendMessage()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        closeConnection()
    }

    /**
     * Closes WebSocket Connection as and when required
     */
    private fun closeConnection() {
        WebSocketConnector.closeConnection()
    }

    /**
     * Initializing WebSocket to establish a connection
     */
    private fun initConnection() {
        WebSocketConnector.openConnection(webSocketListener)
    }

    /**
     * Sends a message to WebSocket
     */
    private fun checkAndSendMessage() {
        val message = edit_text_send_message.text.toString()

        if (!message.isBlank() && isNetworkAvailable()) {
            WebSocketConnector.sendMessage(message = message)
            addData(message = message, messageType = MessageType.SENT)
            edit_text_send_message.setText(String())
        }
    }

    /**
     * Adds message data to RecyclerView
     */
    private fun addData(message: String, messageType: MessageType) {
        runOnUiThread {
            dataList.add(MessageItemDataModel(message = message, messageType = messageType))
            messagesListAdapter.notifyDataSetChanged()
            recycler_view_messages.smoothScrollToPosition(dataList.size - 1)
        }
    }
}