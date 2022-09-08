package otus.gpb.homework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)
        findViewById<Button>(R.id.btnOpenA).setOnClickListener {

        }
        findViewById<Button>(R.id.btnOpenD).setOnClickListener {

        }
        findViewById<Button>(R.id.btnCloseC).setOnClickListener {

        }
        findViewById<Button>(R.id.btnCloseStack).setOnClickListener {

        }
    }
}