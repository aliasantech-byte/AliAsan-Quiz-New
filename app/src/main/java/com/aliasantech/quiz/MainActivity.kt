
package com.aliasantech.quiz

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setBackgroundColor(Color.rgb(15, 25, 50))

        val title = TextView(this)
        title.text = "مسابقه هوش علی آسان"
        title.textSize = 28f
        title.setTextColor(Color.WHITE)
        title.gravity = Gravity.CENTER

        layout.addView(title)

        setContentView(layout)
    }
}
