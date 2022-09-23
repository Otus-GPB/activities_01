package otus.gpb.homework.activities

import android.Manifest
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

        findViewById<Toolbar>(R.id.toolbar).apply {
            inflateMenu(R.menu.menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.send_item -> {
                        //    openSenderApp()
                        true
                    }
                    else -> false
                }
            }
        }

        imageView.setOnClickListener() {
            selectActionDialog()
        }
    }

    private fun openCatPhoto() {
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cat))
    }

    private fun showSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.settings))
            .setMessage(resources.getString(R.string.settings_message))
            .setPositiveButton(resources.getString(R.string.open_settings)) { dialog, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null))
                startActivity(intent)
                dialog.dismiss()
            }

            .show()
    }

    private val launcher= registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            granted ->
        if (granted) {
            openCatPhoto()
        } else if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            showSettingsDialog()
        }
    }

    private fun showRationaleDialog() {
           MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.rationale_title))
            .setMessage(resources.getString(R.string.rationale_message))
            .setPositiveButton(resources.getString(R.string.open_access)) {dialog, _ ->
                launcher.launch(Manifest.permission.CAMERA)
                dialog.dismiss()
            }
            .setNegativeButton(resources.getString(R.string.cancel)) {dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCatPhoto()
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                showRationaleDialog()
            } else {
                launcher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun selectActionDialog() {
        val singleItems = arrayOf(resources.getString(R.string.take_photo), resources.getString(R.string.select_photo))
        val checkedItem = 0

        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title))
            .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                when(which) {
                    0 -> {
                        getPermission()
                        dialog.dismiss()
                    }
                    1 -> dialog.dismiss()
                }
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
}