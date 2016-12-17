package com.tagantroy.rxjava2ws

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class RxMultipleWebSocketListener : WebSocketListener() {

    private val items: MutableList<WebSocketListener> = mutableListOf()

    fun addListener(listener: WebSocketListener) {
        synchronized(this,{
            items.add(listener)
        })
    }

    fun removeListener(listener: WebSocketListener) {
        synchronized(this,{
            items.remove(listener)
        })
    }

    override fun onOpen(webSocket: WebSocket?, response: Response?) {
        toAll { it.onOpen(webSocket, response) }
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
        toAll { it.onFailure(webSocket, t, response) }
    }

    override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
        toAll { it.onClosing(webSocket, code, reason) }
    }

    override fun onMessage(webSocket: WebSocket?, text: String?) {
        toAll { it.onMessage(webSocket, text) }
    }

    override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
        toAll { it.onMessage(webSocket, bytes) }
    }

    override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
        toAll { it.onClosed(webSocket, code, reason) }
    }

    private inline fun toAll(f: (WebSocketListener) -> Unit) {
        synchronized(this,{
            items.forEach {
                f(it)
            }
        })
    }
}