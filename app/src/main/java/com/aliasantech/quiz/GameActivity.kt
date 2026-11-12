
package com.aliasantech.quiz

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class GameActivity : Activity() {

    private data class Question(
        val text: String,
        val options: List<String>,
        val answer: Int
    )

    private val questions = listOf(
        Question("کدام سیاره به خورشید نزدیک‌تر است؟", listOf("زمین", "مریخ", "عطارد", "زهره"), 2),
        Question("حاصل ۸ × ۷ چند است؟", listOf("۵۴", "۵۶", "۶۴", "۴۸"), 1),
        Question("پایتخت ایران کدام شهر است؟", listOf("شیراز", "اصفهان", "تهران", "تبریز"), 2),
        Question("کدام حیوان پستاندار است؟", listOf("کوسه", "دلفین", "تمساح", "مار"), 1),
        Question("یک ساعت چند دقیقه است؟", listOf("۳۰", "۴۵", "۶۰", "۹۰"), 2),
        Question("کدام رنگ از ترکیب آبی و زرد ساخته می‌شود؟", listOf("قرمز", "سبز", "بنفش", "نارنجی"), 1),
        Question("بزرگ‌ترین قاره جهان کدام است؟", listOf("آفریقا", "اروپا", "آسیا", "استرالیا"), 2),
        Question("کدام عدد زوج است؟", listOf("۷", "۹", "۱۳", "۱۸"), 3),
        Question("آب در دمای معمولی چه حالتی دارد؟", listOf("جامد", "مایع", "گاز", "پلاسما"), 1),
        Question("کدام وسیله برای اندازه‌گیری دماست؟", listOf("خط‌کش", "ترازو", "دماسنج", "قطب‌نما"), 2),
        Question("کدام پرنده نمی‌تواند پرواز کند؟", listOf("عقاب", "شترمرغ", "کبوتر", "گنجشک"), 1),
        Question("حاصل ۱۵ + ۲۵ چند است؟", listOf("۳۰", "۳۵", "۴۰", "۴۵"), 2),
        Question("کدام یک ستاره است؟", listOf("ماه", "زمین", "خورشید", "مریخ"), 2),
        Question("چند ضلع در یک مثلث وجود دارد؟", listOf("۲", "۳", "۴", "۵"), 1),
        Question("کدام زبان برای برنامه‌نویسی اندروید کاربرد دارد؟", listOf("Kotlin", "HTML", "CSS", "SQL"), 0),
        Question("کدام جانور خشکی‌زی است؟", listOf("نهنگ", "دلفین", "فیل", "اختاپوس"), 2),
        Question("حاصل ۱۰۰ تقسیم بر ۱۰ چند است؟", listOf("۵", "۱۰", "۲۰", "۱۰۰"), 1),
        Question("کدام فصل بعد از تابستان می‌آید؟", listOf("بهار", "زمستان", "پاییز", "تابستان"), 2),
        Question("کدام اندام برای دیدن استفاده می‌شود؟", listOf("گوش", "چشم", "بینی", "زبان"), 1),
        Question("یک هفته چند روز دارد؟", listOf("۵", "۶", "۷", "۸"), 2)
    )

    private var currentQuestion = 0
    private var score = 0
    private var answered = false

    private lateinit var questionText: TextView
    private lateinit var progressText: TextView
    private lateinit var scoreText: TextView
    private lateinit var answerButtons: List<Button>
    private lateinit var nextButton: Button

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scroll = ScrollView(this)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(dp(20), dp(20), dp(20), dp(20))
        layout.setBackgroundColor(Color.rgb(15, 25, 50))

        progressText = TextView(this)
        progressText.textSize = 18f
        progressText.setTextColor(Color.YELLOW)
        progressText.gravity = Gravity.CENTER

        questionText = TextView(this)
        questionText.textSize = 23f
        questionText.setTextColor(Color.WHITE)
        questionText.gravity = Gravity.CENTER
        questionText.setPadding(0, dp(25), 0, dp(25))

        scoreText = TextView(this)
        scoreText.textSize = 17f
        scoreText.setTextColor(Color.LTGRAY)
        scoreText.gravity = Gravity.CENTER

        layout.addView(progressText)
        layout.addView(questionText)
        layout.addView(scoreText)

        val buttons = mutableListOf<Button>()

        for (i in 0 until 4) {
            val button = Button(this)
            button.textSize = 16f
            buttons.add(button)

            layout.addView(button,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    dp(58)
                )
            )

            button.setOnClickListener {
                selectAnswer(i)
            }
        }

        answerButtons = buttons

        nextButton = Button(this)
        nextButton.text = "سؤال بعدی"
        nextButton.isEnabled = false

        layout.addView(nextButton,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(58)
            )
        )

        scroll.addView(layout)
        setContentView(scroll)

        nextButton.setOnClickListener {
            if (currentQuestion < questions.size - 1) {
                currentQuestion++
                nextButton.text = "سؤال بعدی"
                showQuestion()
            } else {
                showResult()
            }
        }

        showQuestion()
    }

    private fun showQuestion() {
        answered = false

        val question = questions[currentQuestion]

        progressText.text = "سؤال ${currentQuestion + 1} از ${questions.size}"
        questionText.text = question.text
        scoreText.text = "امتیاز: $score"

        for (i in 0 until 4) {
            answerButtons[i].text = question.options[i]
            answerButtons[i].isEnabled = true
        }

        nextButton.isEnabled = false
    }

    private fun selectAnswer(index: Int) {
        if (answered) return

        answered = true

        val correct = questions[currentQuestion].answer

        if (index == correct) {
            score++
            scoreText.text = "آفرین! پاسخ درست بود. امتیاز: $score"
        } else {
            scoreText.text = "پاسخ درست: ${questions[currentQuestion].options[correct]}"
        }

        for (button in answerButtons) {
            button.isEnabled = false
        }

        nextButton.isEnabled = true

        if (currentQuestion == questions.size - 1) {
            nextButton.text = "مشاهده نتیجه"
        }
    }

    private fun showResult() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(dp(24), dp(24), dp(24), dp(24))
        layout.setBackgroundColor(Color.rgb(15, 25, 50))

        val result = TextView(this)
        result.text = "مسابقه تمام شد!\n\nامتیاز شما: $score از ۲۰"
        result.textSize = 26f
        result.setTextColor(Color.WHITE)
        result.gravity = Gravity.CENTER

        val finishButton = Button(this)
        finishButton.text = "بازگشت به صفحه اصلی"
        finishButton.setOnClickListener {
            finish()
        }

        layout.addView(result)
        layout.addView(finishButton)

        setContentView(layout)
    }
}
