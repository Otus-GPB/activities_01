package otus.gpb.homework.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    companion object {
        const val MIME_TYPE_IMAGE = "image/*"
    }

    private lateinit var imageView: ImageView

    private fun takePhoto() {
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cat))
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { populateImage(it) }
    }

    private val cameraPermissionRequestLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        granted ->
        if (granted) {
            takePhoto()
        } else if (!shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            showAppSettingsDialog()
        }
    }

    private fun showAppSettingsDialog() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(R.string.settings_dialog_title)
            setMessage(R.string.setting_dialog_msg)
            setPositiveButton(R.string.settings_dialog_ok) {
                dialog, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
                startActivity(intent)
                dialog.dismiss()
            }
            show()
        }
    }

    private fun showRationaleDialog(permission: String) {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(R.string.rationale_dialog_title)
            setMessage(R.string.rationale_dialog_msg)
            setPositiveButton(R.string.rationale_dialog_ok) {
                dialog, _ -> cameraPermissionRequestLauncher.launch(permission)
                dialog.dismiss()
            }
            setNegativeButton(R.string.rationale_dialog_cancel) {
                dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    private fun getCameraPermission() {
        val permission = android.Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            if (!shouldShowRequestPermissionRationale(permission)) {
                cameraPermissionRequestLauncher.launch(permission)
            } else {
                showRationaleDialog(permission)
            }
        } else {
            takePhoto()
        }
    }

    private fun takeProfileImageDialog() {
        val items = resources.getStringArray(R.array.image_chooser_item)
        MaterialAlertDialogBuilder(this).apply {
            setTitle(R.string.image_chooser_title)
            setItems(items) {
                dialog, which -> when(which) {
                    0 -> getCameraPermission()
                    else -> takePictureLauncher.launch(MIME_TYPE_IMAGE)
                }
                dialog.dismiss()
            }
            show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

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

        imageView.setOnClickListener {
            takeProfileImageDialog()
        }
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