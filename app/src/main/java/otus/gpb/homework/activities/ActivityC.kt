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
            startActivity(Intent(this, ActivityA::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }

        val buttonOpenD = findViewById<Button>(R.id.buttonOpenActivityD)
        buttonOpenD.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        val buttonCloseC = findViewById<Button>(R.id.buttonCloseActivityC)
        buttonCloseC.setOnClickListener {
            finish()
        }

        val buttonCloseStack = findViewById<Button>(R.id.buttonCloseStack)
        buttonCloseStack.setOnClickListener {
            finishAffinity()
        }
    }


}
