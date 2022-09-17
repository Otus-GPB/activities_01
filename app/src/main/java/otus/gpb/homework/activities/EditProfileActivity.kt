package otus.gpb.homework.activities

import android.Manifest.permission.CAMERA
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private val requestPermissionLauncher =
        registerForActivityResult(RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                setCatPhoto()
            } else if (!shouldShowRequestPermissionRationale(CAMERA)) {
                showOpenSettingsDialog()
            }
        }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                populateImage(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

        imageView.setOnClickListener {
            showPhotoActionDialog()
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

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> setCatPhoto()
            shouldShowRequestPermissionRationale(CAMERA) -> showCameraPermissionDialog()
            else -> requestPermissionLauncher.launch(CAMERA)
        }
    }

    private fun showPhotoActionDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.select_action))
            .setItems(
                arrayOf(
                    getString(R.string.make_photo),
                    getString(R.string.select_photo)
                )
            ) { _, which ->
                when (which) {
                    0 -> requestCameraPermission()
                    1 -> getContent.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            }
            .show()
    }

    private fun showCameraPermissionDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.require_camera_access))
            .setMessage(getString(R.string.camera_permission_rational_message))
            .setPositiveButton(getString(R.string.provide_access)) { _, _ ->
                requestPermissionLauncher.launch(CAMERA)
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showOpenSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.require_camera_access))
            .setMessage(getString(R.string.camera_open_settings_message))
            .setPositiveButton(getString(R.string.open_settings)) { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                startActivity(intent)
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

    private fun setCatPhoto() {
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cat))
    }

    private fun openSenderApp() {
        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }
}