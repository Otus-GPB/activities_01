package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_c)
        findViewById<Button>(R.id.GoActivityA).setOnClickListener({
            val intent = Intent(this, ActivityA::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        })
        findViewById<Button>(R.id.GoActivityD).setOnClickListener({
            val intent = Intent(this, ActivityD::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        })
        findViewById<Button>(R.id.CloseC).setOnClickListener({
            onBackPressed()
        })
        findViewById<Button>(R.id.CloseStack).setOnClickListener({
            finishAffinity()
        })
    }
}