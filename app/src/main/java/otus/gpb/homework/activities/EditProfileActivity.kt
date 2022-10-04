package otus.gpb.homework.activities

import android.Manifest
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                isGranted ->
            if (isGranted) {
                makeCatPhoto()
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

        imageView.setOnClickListener(){
            showAlertDialog()
        }
    }
    private fun showAlertDialog(){
        val items = resources.getStringArray(R.array.action_with_photo)

        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title_alert_dialog))
            .setItems(items){dialog, which ->
                when(which) {
                    0 -> {
                        requestPermissionWithRationale()
                        dialog.dismiss()
                    }
                }
            }
            .show()
    }
    private fun requestPermissionWithRationale() {
        when{
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                makeCatPhoto()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) -> {
                showInfoDialog()
            }
            else -> {
            requestPermission()
            }
        }
    }

    private fun requestPermission() {
        cameraLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun getSetting() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.setting_title))
            .setMessage(resources.getString(R.string.setting_message))
            .setPositiveButton(resources.getString(R.string.setting_open)){
                _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:$packageName"))
                startActivity(intent)
            }
            .show()
    }


    private fun makeCatPhoto(){
        imageView.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.cat)
        )
    }

    private fun showInfoDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.info_dialog_title))
            .setMessage(resources.getString(R.string.info_dialog_message))
            .setNegativeButton(resources.getString(R.string.info_dialog_cancel)){
                dialog, _ ->
                getSetting()
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.info_dialog_allow)){
                dialog, _ ->
                cameraLauncher.launch(Manifest.permission.CAMERA)
                dialog.dismiss()
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