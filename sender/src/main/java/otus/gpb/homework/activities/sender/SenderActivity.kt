package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender_activity)
        findViewById<Button>(R.id.btn_gotoGoogleMaps).setOnClickListener {
            try {
                val geoUriRestourantsString = "geo:0,0?z=10&q=рестораны"
                val geoUriR: Uri = Uri.parse(geoUriRestourantsString)
                val mapIntent = Intent(Intent.ACTION_VIEW, geoUriR)
                mapIntent.setPackage("com.google.android.apps.maps")
                mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(mapIntent)
            } catch (e: Exception) {
                Log.e("EXP", "${e.message} ошибочка - no GoogleMaps")
                Toast.makeText(this, "Oops! Your phone has no GoogleMaps!\nAdd this app and try again!", Toast.LENGTH_LONG).show()
            }
        }

    }
}

