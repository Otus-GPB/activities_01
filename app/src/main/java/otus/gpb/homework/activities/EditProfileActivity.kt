package otus.gpb.homework.activities

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

const val ACTION_MAKE_PHOTO = 0
const val ACTION_SELECT_PHOTO = 1

const val TAG = "EditProfileActivity"

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var textViewFirstName: TextView
    private lateinit var textViewSecondName: TextView
    private lateinit var textViewAge: TextView

    private var imageUri :Uri? = null

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                isGranted ->
            when {
                isGranted -> populateImageWithDefaultPhoto()
                !ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.CAMERA) -> showCameraSettingsDialog()
            }
        }

    private val settingsLauncher = registerForActivityResult(
        object: ActivityResultContract<String, Boolean>() {
            override fun createIntent(context: Context, input: String): Intent =
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:$packageName")
                )
            override fun parseResult(resultCode: Int, intent: Intent?): Boolean =
                ContextCompat.checkSelfPermission(
                    this@EditProfileActivity, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        if (it) {
            populateImageWithDefaultPhoto()
        }
    }

    private val galleryPickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {  uri ->
        uri?.let {
            imageUri = uri
            populateImage(it)
        }
    }

    private val fillFormLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
            result ->
            when (result.resultCode) {
                RESULT_OK -> result.data?.let {
                    textViewFirstName.text = it.getStringExtra(FillFormActivity.EXTRA_FIRST_NAME)
                    textViewSecondName.text = it.getStringExtra(FillFormActivity.EXTRA_SECOND_NAME)
                    textViewAge.text = it.getStringExtra(FillFormActivity.EXTRA_AGE)
                }
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById<ImageView?>(R.id.imageview_photo).apply {
            setOnClickListener { onAvatarClick() }
        }

        textViewFirstName = findViewById(R.id.textview_name)
        textViewSecondName = findViewById(R.id.textview_surname)
        textViewAge = findViewById(R.id.textview_age)

        findViewById<Button>(R.id.buttonFillForm).setOnClickListener {
            fillFormLauncher.launch(Intent(this, FillFormActivity::class.java))
        }

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
    }

    private fun onAvatarClick() {
        val items = arrayOf(
            getString(R.string.make_photo),
            getString(R.string.select_photo)
        )

        MaterialAlertDialogBuilder(this)
            .setItems(items) { _, which ->
                when (which) {
                    ACTION_MAKE_PHOTO -> makePhoto()
                    ACTION_SELECT_PHOTO -> galleryPickerLauncher.launch("image/*")
                }
            }
            .show()
    }

    private fun makePhoto() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) -> populateImageWithDefaultPhoto()
            else -> requestCameraPermissionRationale()
        }
    }

    private fun requestCameraPermissionRationale() {
        when {
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) ->
                showCameraRationaleDialog()

            else -> requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun populateImageWithDefaultPhoto() {
        imageView.setImageResource(R.drawable.cat)
    }

    private fun showCameraRationaleDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.camera_access)
            .setMessage(R.string.camera_rationale)
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(R.string.give_access) { _, _ ->
                requestCameraPermission()
            }
            .show()
    }

    private fun showCameraSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.camera_access)
            .setMessage(R.string.camera_settings_rationale)
            .setPositiveButton(R.string.open_settings) { _, _ ->
                settingsLauncher.launch("")
            }
            .show()
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            setPackage("org.telegram.messenger")
            imageUri?.let { putExtra(Intent.EXTRA_STREAM, imageUri) }
            putExtra(
                Intent.EXTRA_TEXT,
                "${textViewFirstName.text}\n${textViewSecondName.text}\n${textViewAge.text}"
            )
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, "Error starting telegram: ${e.message}")
            Snackbar.make(
                findViewById<Button>(R.id.buttonFillForm),
                R.string.telegram_is_not_installed,
                Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}