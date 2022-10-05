package otus.gpb.homework.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

const val ACTION_MAKE_PHOTO = 0
const val ACTION_SELECT_PHOTO = 1

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

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
        uri?.let { populateImage(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById<ImageView?>(R.id.imageview_photo).apply {
            setOnClickListener { onAvatarClick() }
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
                    ACTION_SELECT_PHOTO -> pickPhotoFromGallery()
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
                openSettings()
            }
            .show()
    }

    private fun openSettings() {
        settingsLauncher.launch("")
    }

    private fun pickPhotoFromGallery() {
        galleryPickerLauncher.launch("image/*")
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }
}