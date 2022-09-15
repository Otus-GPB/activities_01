package otus.gpb.homework.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private val launcherPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted: Boolean ->
            if (granted) {
                getPicture()
            } else if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                openSettingsDialog()
            }
        }

    private val getPhotoLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            imageUri?.let {
                populateImage(it)
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

            val items = arrayOf(getString(R.string.make_photo), getString(R.string.choose_photo))

            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.choose_method))
                .setItems(items) { dialog, which ->
                    when (which) {
                        0 -> checkPermissionWithRationale()
                        1 -> getPhotoLauncher.launch(MIME_TYPE)
                    }
                }
                .show()
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getPicture() {
        findViewById<ImageView>(R.id.imageview_photo).setImageDrawable(getDrawable(R.drawable.cat))
    }


    private fun checkPermissionWithRationale() {

        val permissionCheck = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )

        val requestRationale: Boolean = shouldShowRequestPermissionRationale(
            Manifest.permission.CAMERA
        )

        when {
            permissionCheck == PackageManager.PERMISSION_GRANTED -> getPicture()
            requestRationale -> showRationalDialog()

            else -> launcherPermission.launch(
                Manifest.permission.CAMERA
            )

        }


    }


    private fun showRationalDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.get_permission))
            .setMessage(getString(R.string.get_access_to_camera))
            .setNegativeButton(getString(R.string.denie_access)) { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                launcherPermission.launch(Manifest.permission.CAMERA)

            }
            .show()
    }

    private fun openSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.open_settings))
            .setMessage(getString(R.string.open_settings_for_permissions))
            .setPositiveButton(getString(R.string.good)) { dialog, which ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:$packageName")
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

    private fun openSenderApp() {
        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }

    companion object {
        private const val MIME_TYPE = "image/*"
    }
}