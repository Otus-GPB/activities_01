package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder


private const val TAG = "EditProfileActivity"
private const val PERMISSION_REQUEST_CAMERA = 0

class EditProfileActivity : AppCompatActivity(), PersonalData {

    private lateinit var imageView: ImageView
    private lateinit var name: TextView
    private lateinit var surname: TextView
    private lateinit var age: TextView
    private var _uri: Uri? = null


    private val editProfile = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        object : ActivityResultCallback<ActivityResult?> {
            override fun onActivityResult(result: ActivityResult?) {
                result ?: return
                if (result.resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "result from fillFormActivity OK")
                    name.text = result.data?.getStringExtra(PersonalData.NAME)
                    surname.text = result.data?.getStringExtra(PersonalData.SURNAME)
                    age.text = result.data?.getStringExtra(PersonalData.AGE)
                }
            }
        }
    )

    private val selectPhoto =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            _uri = uri
            uri?.let {
                populateImage(uri)
            }
        }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            imageView.setImageResource(R.drawable.cat)
        } else {
            requestPermissionWithRationale()
        }
    }


    private fun requestPermissionWithRationale() {
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                imageView.setImageResource(R.drawable.cat)
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.CAMERA
            ) -> {
                // show dialog, invoke requestPermission() when confirm
                openAppSettings()
            }
            else -> showRationaleDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        name = findViewById(R.id.textview_name)
        surname = findViewById(R.id.textview_surname)
        age = findViewById(R.id.textview_age)

        findViewById<Toolbar>(R.id.toolbar).apply {
            inflateMenu(R.menu.menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.send_item -> {
                        openSenderApp()
                        true
                    }
                    else -> false
                }
            }
        }
        findViewById<ImageView>(R.id.imageview_photo).setOnClickListener {
            val items = arrayOf("Сделать фото", "Выбрать фото")

            MaterialAlertDialogBuilder(this)
                .setItems(items) { _, which ->
                    when (which) {
                        0 -> launcher.launch(Manifest.permission.CAMERA)
                        1 -> selectPhoto.launch("image/")
                    }
                }.show()
        }
        findViewById<Button>(R.id.button_edit).setOnClickListener {
            editProfile.launch(
                Intent(
                    this,
                    FillFormActivity::class.java
                )
            )
        }

    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CAMERA
        )
        imageView.setImageResource(R.drawable.cat)
    }

    private fun showRationaleDialog() {
        Log.d(TAG, "Show rational dialog")
        val items = arrayOf("Дать доступ", "Отмена")

        MaterialAlertDialogBuilder(this)
            .setItems(items) { _, which ->
                when (which) {
                    0 -> requestCameraPermission()
                    1 -> showOpenSettingsDialog()
                }
            }.show()
    }

    private fun showOpenSettingsDialog() {
        val items = arrayOf("Открыть настройки")

        MaterialAlertDialogBuilder(this)
            .setItems(items) { _, which ->
                when (which) {
                    0 -> openAppSettings()
                }
            }.show()
    }

    private fun openAppSettings() {
        val intent = Intent(android.provider.Settings.ACTION_SETTINGS)
        startActivity(intent)
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        try {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                setPackage("org.telegram.messenger")
                putExtra(Intent.EXTRA_TEXT, "${name.text} ${surname.text} ${age.text}")
                putExtra(Intent.EXTRA_STREAM, _uri)
                type = "*/*"
            }
            startActivity(sendIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Telegram not found", Toast.LENGTH_SHORT).show()
            Log.e(TAG, e.message.toString())
        }

    }
}