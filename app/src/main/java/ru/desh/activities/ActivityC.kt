package ru.desh.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityC : AppCompatActivity() {
    companion object {
        private val TAG = "ActivityC"
    }

    private val onClickStartA = View.OnClickListener {
        val i = Intent(this, ActivityA::class.java)
        startActivity(i)
    }
    private val onClickStartD = View.OnClickListener {
        val i = Intent(this, ActivityD::class.java)
        startActivity(i)
        finishAffinity()
    }

    private val onClickCloseC = View.OnClickListener { finish() }

    private val onClickCloseStack = View.OnClickListener {
        val i = Intent(this, ActivityA::class.java)
        startActivity(i)
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        findViewById<Button>(R.id.button_open_a).setOnClickListener(onClickStartA)
        findViewById<Button>(R.id.button_open_d).setOnClickListener(onClickStartD)
        findViewById<Button>(R.id.button_close_c).setOnClickListener(onClickCloseC)
        findViewById<Button>(R.id.button_close_stack).setOnClickListener(onClickCloseStack)
    }
}