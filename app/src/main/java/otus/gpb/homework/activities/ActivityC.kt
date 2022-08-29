package otus.gpb.homework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)
        val btnOpenA = findViewById<Button>(R.id.btn_open_a)
        btnOpenA.setOnClickListener {

        }
        val btnOpenD = findViewById<Button>(R.id.btn_open_d)
        btnOpenD.setOnClickListener {

        }
        val btnCloseC = findViewById<Button>(R.id.btn_close_c)
        btnCloseC.setOnClickListener {

        }
        val btnCloseStack = findViewById<Button>(R.id.btn_close_stack)
        btnCloseStack.setOnClickListener {

        }
    }
}