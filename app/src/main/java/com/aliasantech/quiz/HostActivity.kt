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

class HostActivity : Activity() {

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
        ipText.text = "آدرس IP گوشی:\n${getLocalIp()}"
        ipText.textSize = 20f
        ipText.setTextColor(Color.YELLOW)
        ipText.gravity = Gravity.CENTER

        val info = TextView(this)
        info.text = "برای اتصال، هر دو گوشی باید به یک شبکه Wi-Fi متصل باشند."
        info.textSize = 16f
        info.setTextColor(Color.LTGRAY)
        info.gravity = Gravity.CENTER

        val startButton = Button(this)
        startButton.text = "شروع مسابقه"
        startButton.isEnabled = false

        layout.addView(title)
        layout.addView(ipText)
        layout.addView(info)

        layout.addView(startButton,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(60)
            )
        )

        setContentView(layout)
    }
}
