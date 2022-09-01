package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

private const val TAG = "ActivityC"
class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)
        val btnOpenA = findViewById<Button>(R.id.btn_open_a)
        btnOpenA.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }
        val btnOpenD = findViewById<Button>(R.id.btn_open_d)
        btnOpenD.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        val btnCloseC = findViewById<Button>(R.id.btn_close_c)
        btnCloseC.setOnClickListener {
            onBackPressed()
        }
        val btnCloseStack = findViewById<Button>(R.id.btn_close_stack)
        btnCloseStack.setOnClickListener {
            finishAffinity()
        }
    }
}