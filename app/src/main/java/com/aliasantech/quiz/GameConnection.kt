
package com.aliasantech.quiz

import java.net.Socket
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

object GameConnection {

    @Volatile
    private var socket: Socket? = null

    private var reader: BufferedReader? = null
    private var writer: BufferedWriter? = null

    @Volatile
    var isHost: Boolean = false

    @Synchronized
    fun connect(newSocket: Socket, host: Boolean) {
        socket = newSocket
        isHost = host

        reader = BufferedReader(
            InputStreamReader(newSocket.getInputStream(), Charsets.UTF_8)
        )

        writer = BufferedWriter(
            OutputStreamWriter(newSocket.getOutputStream(), Charsets.UTF_8)
        )
    }

    @Synchronized
    fun send(message: String) {
        val output = writer ?: throw IllegalStateException("اتصال برقرار نیست")

        output.write(message)
        output.newLine()
        output.flush()
    }

    fun receive(): String? {
        return reader?.readLine()
    }

    @Synchronized
    fun close() {
        try {
            socket?.close()
        } catch (e: Exception) {
        }

        socket = null
        reader = null
        writer = null
    }
}
