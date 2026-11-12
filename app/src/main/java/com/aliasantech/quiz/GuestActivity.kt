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
import android.widget.Toast

class GuestActivity : Activity() {

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

        val ipInput = EditText(this)
        ipInput.hint = "آدرس IP میزبان را وارد کنید"
        ipInput.setSingleLine(true)
        ipInput.inputType = 0x00001001

        val connectButton = Button(this)
        connectButton.text = "اتصال به میزبان"

        val info = TextView(this)
        info.text = "آدرس IP را از گوشی میزبان دریافت کنید."
        info.textSize = 16f
        info.setTextColor(Color.LTGRAY)
        info.gravity = Gravity.CENTER

        layout.addView(title)

        layout.addView(ipInput,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(60)
            )
        )

        layout.addView(info)

        layout.addView(connectButton)

        setContentView(layout)

        connectButton.setOnClickListener {
            val ip = ipInput.text.toString().trim()

            if (ip.isEmpty()) {
                ipInput.error = "آدرس IP را وارد کنید"
            } else {
                Toast.makeText(
                    this,
                    "اتصال در مرحله بعد فعال می‌شود",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
