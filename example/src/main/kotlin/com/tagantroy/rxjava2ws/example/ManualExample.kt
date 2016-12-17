package com.tagantroy.rxjava2ws.example

import com.tagantroy.rxjava2ws.RxWebSocket
import com.tagantroy.rxjava2ws.WebSocketEvent
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.Request
import java.util.concurrent.TimeUnit


/**
 * Created by ivanbalaksha on 17.12.16.
 */

object ManualExample {
    @JvmStatic
    fun main(args: Array<String>) {
        val request = Request.Builder()
                .url("ws://echo.websocket.org")
                .get()
                .build()
        val rxWS = RxWebSocket.createManualRxWebSocket(request)
        val disposable = rxWS.observe()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    when (it) {
                        is WebSocketEvent.StringMessageEvent -> {
                            println("received: ${it.text}")
                        }
                        else -> {
                            println("received: $it")
                        }
                    }
                })
        rxWS.connect()
        Observable.intervalRange(0, 100, 100, 100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map {
                    val msg = it.toString()
                    println("try send: $msg")
                    Pair(msg, rxWS.send(msg))
                }.filter { it.second }
                .subscribe {
                    println("sent: ${it.first}")
                }

        Thread.sleep(3000)
        rxWS.close(1000,"ok")
        disposable.dispose()
    }
}