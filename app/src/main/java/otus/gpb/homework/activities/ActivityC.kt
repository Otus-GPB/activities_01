package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)
        Log.i("CREATED: ", this.toString())

        val buttonOpenA = findViewById<Button>(R.id.act_c_button_a)
        buttonOpenA.setOnClickListener {
            startActivity(Intent(this, ActivityA::class.java))
        }

        val buttonOpenD = findViewById<Button>(R.id.act_c_button_d)
        buttonOpenD.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, ActivityD::class.java))
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


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i("ON INTENT: ", this.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("DESTROYED: ", this.toString())
    }
}