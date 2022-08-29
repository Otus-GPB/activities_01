package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        val buttonOpenA = findViewById<Button>(R.id.act_c_button_a)
        buttonOpenA.setOnClickListener {
            startActivity(Intent(this, ActivityA::class.java))
        }

        val buttonOpenD = findViewById<Button>(R.id.act_c_button_d)
        buttonOpenD.setOnClickListener {
            startActivity(Intent(this, ActivityD::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }

        val buttonCloseC = findViewById<Button>(R.id.act_c_button_close)
        buttonCloseC.setOnClickListener {
            super.onBackPressed()
        }

        val buttonCloseStack = findViewById<Button>(R.id.act_c_button_close_stack)
        buttonCloseStack.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, ActivityA::class.java))
        }
    }
}