package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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

class EditProfileActivity : AppCompatActivity() {

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
                    name.text = result.data?.getStringExtra(NAME)
                    surname.text = result.data?.getStringExtra(SURNAME)
                    age.text = result.data?.getStringExtra(AGE)
                }
            }
        }
    )

    private val selectPhoto =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            _uri = uri
            uri?.let { imageView.setImageURI(uri) }
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
        findViewById<ImageView>(R.id.imageview_photo).setOnClickListener { takePhoto() }
        findViewById<Button>(R.id.button_edit).setOnClickListener {
            editProfile.launch(
                Intent(
                    this,
                    FillFormActivity::class.java
                )
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.isEmpty()) {
                Log.d(TAG, "PERMISSION_REQUEST_CAMERA do not have")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "есть разрешение")
                startCamera()
            } else {
                Log.d(TAG, "ничего не делать!")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun takePhoto() {
        val items = arrayOf("Сделать фото", "Выбрать фото")

        MaterialAlertDialogBuilder(this)
            .setItems(items) { _, which ->
                when (which) {
                    0 -> showCameraPreview()
                    1 -> selectPhoto.launch("image/")
                }
            }.show()
    }


    private fun showCameraPreview() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            doCameraPermission()
        }
    }

    private fun doCameraPermission() {
        Log.d(TAG, "Requested camera permission")
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            showRationaleDialog()
        } else {
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CAMERA
        )
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

    private fun startCamera() {
        Log.d(TAG, "Start Camera")
        imageView.setImageResource(R.drawable.cat)
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${name.text} ${surname.text} ${age.text}")
            putExtra(Intent.EXTRA_STREAM, _uri)
            type = "*/*"
        }
        startActivity(sendIntent)
    }


    companion object {
        const val NAME = "name_from_fill_form"
        const val SURNAME = "surname_from_fill_form"
        const val AGE = "age_from_fill_form"
    }
}