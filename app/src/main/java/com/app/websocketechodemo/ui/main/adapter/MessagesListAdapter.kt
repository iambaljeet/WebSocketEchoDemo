package com.app.websocketechodemo.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.websocketechodemo.R
import com.app.websocketechodemo.model.DiffUtilMessages
import com.app.websocketechodemo.model.MessageItemDataModel
import com.app.websocketechodemo.model.MessageType
import kotlinx.android.synthetic.main.my_message_list_item.view.*
import kotlinx.android.synthetic.main.others_message_list_item.view.*

class MessagesListAdapter: ListAdapter<MessageItemDataModel, RecyclerView.ViewHolder>(
    DiffUtilMessages()
) {
    private val ITEM_TYPE_MY_MESSAGE = 100
    private val ITEM_TYPE_OTHERS_MESSAGE = 125

    /**
     * Initializes ViewHolders based on current data.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_TYPE_MY_MESSAGE -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_message_list_item, parent, false)
                MyMessagesViewHolder(itemView = itemView)
            }
            ITEM_TYPE_OTHERS_MESSAGE -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.others_message_list_item, parent, false)
                OthersMessagesViewHolder(itemView = itemView)
            }
            else -> { throw IllegalStateException("Message type is not valid") }
        }
    }

    /**
     * Binds data to the UI using ViewHolders.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messageItemDataModel = currentList[position]

        when(holder) {
            is MyMessagesViewHolder -> {
                holder.bind(messageItemDataModel)
            }
            is OthersMessagesViewHolder -> {
                holder.bind(messageItemDataModel)
            }
        }
    }

    /**
     * Returns specific type for different types of views based on the current list item.
     */
    override fun getItemViewType(position: Int): Int {
        val messageItemDataModel = currentList[position]
        return when (messageItemDataModel.messageType) {
            MessageType.SENT -> {
                ITEM_TYPE_MY_MESSAGE
            }
            MessageType.RECEIVED -> {
                ITEM_TYPE_OTHERS_MESSAGE
            }
        }
    }
}

/**
 * ViewHolder for own messages
 */
class MyMessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(messageItemDataModel: MessageItemDataModel?) {
        itemView.text_view_my_message.text = messageItemDataModel?.message
    }
}

/**
 * ViewHolder for received/other messages
 */
class OthersMessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(messageItemDataModel: MessageItemDataModel?) {
        itemView.text_view_response_message.text = messageItemDataModel?.message
    }
}