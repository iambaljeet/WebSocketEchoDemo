package com.app.websocketechodemo.model

import androidx.recyclerview.widget.DiffUtil

data class MessageItemDataModel(
    val message: String,
    val messageType: MessageType
)

/**
 * DiffUtil class to check difference between list items.
 */
class DiffUtilMessages: DiffUtil.ItemCallback<MessageItemDataModel>() {
    override fun areItemsTheSame(oldItem: MessageItemDataModel, newItem: MessageItemDataModel): Boolean {
        return newItem.message == oldItem.message
    }

    override fun areContentsTheSame(oldItem: MessageItemDataModel, newItem: MessageItemDataModel): Boolean {
        return newItem == oldItem
    }
}