package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class ActivityB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        Log.i("CREATED: ", this.toString())

        val button = findViewById<Button>(R.id.act_b_button)
        button.setOnClickListener {
            startActivity(Intent(this, ActivityC::class.java))
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