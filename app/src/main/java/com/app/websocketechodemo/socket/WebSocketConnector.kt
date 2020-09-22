package com.app.websocketechodemo.socket

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

private const val TAG = "WebSocketConnector"
object WebSocketConnector {
    private val WEBSOCKET_URL = "wss://echo.websocket.org"
    private val CLOSED_NORMALLY = 1000
    private val CLOSURE_REASON = "Web socket closed by app."

    private val okHttpClient = OkHttpClient()
    private lateinit var webSocket: WebSocket

    /**
     * Creates a connection between client and WebSocket server
     */
    fun openConnection(webSocketListener: WebSocketListener) {
        val request = Request.Builder()
            .url(WEBSOCKET_URL)
            .build()

        webSocket = okHttpClient.newWebSocket(request = request, listener = webSocketListener)
    }

    /**
     * Closes a connection between client and WebSocket server
     */
    fun closeConnection() {
        if (this::webSocket.isInitialized) {
            webSocket.close(CLOSED_NORMALLY, CLOSURE_REASON)
        }
    }

    /**
     * Sends a new message to the socket server
     */
    fun sendMessage(message: String) {
        if (this::webSocket.isInitialized) {
            webSocket.send(message)
        }
    }
}