package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)
        val buttonOpenA = findViewById<Button>(R.id.buttonOpenActivityA)
        buttonOpenA.setOnClickListener {
            startActivity(Intent(this, ActivityA::class.java))
        }

        val buttonOpenD = findViewById<Button>(R.id.buttonOpenActivityD)
        buttonOpenD.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val buttonCloseC = findViewById<Button>(R.id.buttonCloseActivityC)
        buttonCloseC.setOnClickListener {
           finish()
        }

        val buttonCloseStack = findViewById<Button>(R.id.buttonCloseStack)
        buttonCloseStack.setOnClickListener {
            finishAffinity() // позволяет закрыть стек целиком
        }
    }




}
//В layout файл ActivityC
// добавьте кнопки с текстом “Open ActivityA”,
// “Open ActivityD”, “Close ActivityC”, “Close Stack”