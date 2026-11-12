
package com.aliasantech.quiz

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

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
        title.text = "مسابقه هوش علی آسان"
        title.textSize = 28f
        title.setTextColor(Color.WHITE)
        title.gravity = Gravity.CENTER

        val subtitle = TextView(this)
        subtitle.text = "آماده رقابت فکری هستی؟"
        subtitle.textSize = 18f
        subtitle.setTextColor(Color.LTGRAY)
        subtitle.gravity = Gravity.CENTER

        val hostButton = Button(this)
        hostButton.text = "ساخت بازی (میزبان)"

        val guestButton = Button(this)
        guestButton.text = "ورود به بازی (مهمان)"

        layout.addView(title)
        layout.addView(subtitle)

        layout.addView(hostButton,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(60)
            )
        )

        layout.addView(guestButton,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(60)
            )
        )

        setContentView(layout)

        hostButton.setOnClickListener {
            startActivity(Intent(this, HostActivity::class.java))
        }

        guestButton.setOnClickListener {
            startActivity(Intent(this, GuestActivity::class.java))
        }
    }
}
