package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                makePhoto()
            }
            else if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.CAMERA)
            ) {
                showSettingsDialog()
            }
        }

    private val settingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) {
            cameraLauncher.launch(Manifest.permission.CAMERA)
        }

    private val fillFormLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult? ->
            if (result?.resultCode == Activity.RESULT_OK) {
                val intent: Intent? = result.data
                val viewName = findViewById<TextView>(R.id.textview_name)
                val viewSurname = findViewById<TextView>(R.id.textview_surname)
                val viewAge = findViewById<TextView>(R.id.textview_age)
                viewName.text =
                    intent?.getStringExtra(FillFormActivity.EXTRA_RESULT_FIRST_NAME) ?: ""
                viewSurname.text =
                    intent?.getStringExtra(FillFormActivity.EXTRA_RESULT_SECOND_NAME) ?: ""
                viewAge.text = intent?.getStringExtra(FillFormActivity.EXTRA_RESULT_AGE) ?: ""
            }
        }

    private val selectImageFromGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                populateImage(uri)
                imageView.tag = uri.toString()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.tag = FLAG_NO_URI
        customizeImageView()
        findViewById<Button>(R.id.button4).setOnClickListener {
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

    private fun customizeImageView() {
        imageView.setOnClickListener {
            val items = arrayOf(getString(R.string.make_photo), getString(R.string.choose_photo))
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.select_please))
                .setItems(items) { _, idx ->
                    if (idx == 0) {
                        requestCameraPermissionWithRationale()
                    }
                    else {
                        selectImageFromGalleryLauncher.launch("image/*")
                    }
                }
                .show()
        }
    }

    private fun makePhoto() {
        imageView.setImageResource(R.drawable.cat)
    }

    private fun openApplicationSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        )
        settingsLauncher.launch(appSettingsIntent)
    }

    private fun requestCameraPermissionWithRationale() {
        when {
            ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED -> {
                makePhoto()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.CAMERA
            ) -> {
                showPermissionDialog()
            }
            else -> {
                cameraLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        var text: String = findViewById<TextView>(R.id.textview_name).text.toString() + "\n"
        text += findViewById<TextView>(R.id.textview_surname).text.toString() + "\n"
        text += findViewById<TextView>(R.id.textview_age).text.toString() + "\n"
        val uri: Uri? =
            if (imageView.tag.toString() == FLAG_NO_URI)
                null
            else
                Uri.parse(imageView.tag.toString())
        intent = Intent(Intent.ACTION_SEND).apply {
            type = "*/*"
            `package` = "org.telegram.messenger"
            if (uri != null)
                putExtra(Intent.EXTRA_STREAM, Uri.parse(imageView.tag.toString()))
            putExtra(Intent.EXTRA_TEXT, text)
        }
        try {
            startActivity(intent)
        }
        catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Telegram not found: $e", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showPermissionDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.permission_dialog_tittle))
            .setMessage(getString(R.string.permission_dialog_message))
            .setNegativeButton(
                getString(R.string.permission_dialog_neg_button)
            ) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(
                getString(R.string.permission_dialog_pos_button)
            ) { _, _ ->
                cameraLauncher.launch(Manifest.permission.CAMERA)
            }
            .show()
    }

    private fun showSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.settings_dialog_tittle))
            .setMessage(getString(R.string.settings_dialog_message))
            .setPositiveButton(getString(R.string.settings_dialog_button)) { _, _ ->
                openApplicationSettings()
            }
            .show()
    }

    companion object {
        private const val FLAG_NO_URI: String = "NO URI"
    }
}