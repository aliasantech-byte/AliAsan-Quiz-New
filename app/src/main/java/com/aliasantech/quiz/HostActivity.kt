
package com.aliasantech.quiz

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.net.NetworkInterface
import java.net.Inet4Address
import java.net.ServerSocket

class HostActivity : Activity() {

    private var serverSocket: ServerSocket? = null
    private lateinit var statusText: TextView

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }

    private fun getLocalIp(): String {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()

            while (interfaces.hasMoreElements()) {
                val network = interfaces.nextElement()
                val addresses = network.inetAddresses

                while (addresses.hasMoreElements()) {
                    val address = addresses.nextElement()

                    if (address is Inet4Address &&
                        !address.isLoopbackAddress) {
                        return address.hostAddress ?: "نامشخص"
                    }
                }
            }
        } catch (e: Exception) {
            return "نامشخص"
        }

        return "نامشخص"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(dp(24), dp(24), dp(24), dp(24))
        layout.setBackgroundColor(Color.rgb(15, 25, 50))

        val title = TextView(this)
        title.text = "اتاق میزبان"
        title.textSize = 28f
        title.setTextColor(Color.WHITE)
        title.gravity = Gravity.CENTER

        val ipText = TextView(this)
        ipText.text = "آدرس IP گوشی:\n${getLocalIp()}\nپورت: 5050"
        ipText.textSize = 20f
        ipText.setTextColor(Color.YELLOW)
        ipText.gravity = Gravity.CENTER

        statusText = TextView(this)
        statusText.text = "هنوز سرور شروع نشده است"
        statusText.textSize = 16f
        statusText.setTextColor(Color.LTGRAY)
        statusText.gravity = Gravity.CENTER

        val startButton = Button(this)
        startButton.text = "روشن کردن میزبان"

        layout.addView(title)
        layout.addView(ipText)
        layout.addView(statusText)
        layout.addView(startButton)

        setContentView(layout)

        startButton.setOnClickListener {
            startButton.isEnabled = false
            statusText.text = "در حال آماده‌سازی سرور..."

            Thread {
                try {
                    val socket = ServerSocket(5050)
                    serverSocket = socket

                    runOnUiThread {
                        statusText.text = "سرور روشن است؛ منتظر گوشی مهمان..."
                    }

                    val client = socket.accept()

                    runOnUiThread {
                        statusText.text = "یک گوشی به میزبان وصل شد!"
                    }

                    client.close()

                } catch (e: Exception) {
                    runOnUiThread {
                        statusText.text = "خطا در راه‌اندازی سرور"
                        startButton.isEnabled = true
                    }
                }
            }.start()
        }
    }

    override fun onDestroy() {
        try {
            serverSocket?.close()
        } catch (e: Exception) {
        }

        super.onDestroy()
    }
}
