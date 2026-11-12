
package com.aliasantech.quiz

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import java.net.Socket

class GuestActivity : Activity() {

    private lateinit var statusText: TextView
    private lateinit var ipInput: EditText

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(dp(24), dp(24), dp(24), dp(24))
        layout.setBackgroundColor(Color.rgb(15, 25, 50))

        val title = TextView(this)
        title.text = "ورود به مسابقه"
        title.textSize = 28f
        title.setTextColor(Color.WHITE)
        title.gravity = Gravity.CENTER

        ipInput = EditText(this)
        ipInput.hint = "آدرس IP میزبان را وارد کنید"
        ipInput.setSingleLine(true)
        ipInput.inputType = 0x00001001

        val connectButton = Button(this)
        connectButton.text = "اتصال به میزبان"

        statusText = TextView(this)
        statusText.text = "هر دو گوشی باید به یک Wi-Fi متصل باشند."
        statusText.textSize = 16f
        statusText.setTextColor(Color.LTGRAY)
        statusText.gravity = Gravity.CENTER

        layout.addView(title)

        layout.addView(ipInput,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(60)
            )
        )

        layout.addView(connectButton)
        layout.addView(statusText)

        setContentView(layout)

        connectButton.setOnClickListener {
            val ip = ipInput.text.toString().trim()

            if (ip.isEmpty()) {
                ipInput.error = "آدرس IP را وارد کنید"
                return@setOnClickListener
            }

            connectButton.isEnabled = false
            statusText.text = "در حال اتصال..."

            Thread {
                var socket: Socket? = null

                try {
                    socket = Socket(ip, 5050)

                    runOnUiThread {
                        statusText.text = "اتصال به میزبان موفق بود!"
                    }

                    // اتصال فعلاً آزمایشی است.
                    // ارتباط تا زمان بسته‌شدن صفحه برقرار می‌ماند.

                    while (!socket.isClosed) {
                        Thread.sleep(500)
                    }

                } catch (e: Exception) {
                    runOnUiThread {
                        statusText.text = "اتصال ناموفق بود؛ IP و Wi-Fi را بررسی کن."
                        connectButton.isEnabled = true
                    }
                } finally {
                    try {
                        socket?.close()
                    } catch (e: Exception) {
                    }
                }
            }.start()
        }
    }
}
