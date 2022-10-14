package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private lateinit var tvName:TextView
    private lateinit var tvSurname:TextView
    private lateinit var tvAge:TextView

    private lateinit var imageFromGallery :Uri

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when{
                granted -> {
                    makeCatPhoto()
                }
            }
        }
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { populateImage(it) }
    }

    private val fillFormLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
                result ->
                if(result.resultCode == RESULT_OK){
                    result.data?.let{
                        tvName.text = it.getStringExtra("name")
                        tvSurname.text = it.getStringExtra("surname")
                        tvAge.text = it.getStringExtra("age")
                    }
                }

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

        tvName = findViewById<TextView>(R.id.textview_name)
        tvSurname = findViewById<TextView>(R.id.textview_surname)
        tvAge = findViewById<TextView>(R.id.textview_age)

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
        findViewById<Button>(R.id.button4).setOnClickListener(){
            fillFormLauncher.launch(Intent(this, FillFromActivity::class.java))
        }

    }
    private fun showAlertDialog(){
        val items = resources.getStringArray(R.array.action_with_photo)

        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title_alert_dialog))
            .setItems(items){_, which ->
                when(which) {
                    0 -> {
                        if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                            showInfoDialog()
                        }else{
                            cameraLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                    1 ->{
                        selectImageFromGallery()
                    }
                }
            }
            .show()
    }

    private fun selectImageFromGallery() {
        galleryLauncher.launch("image/*")
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
                _, _ ->
                getSetting()
            }
            .setPositiveButton(resources.getString(R.string.info_dialog_allow)){
                _ , _ ->
                cameraLauncher.launch(Manifest.permission.CAMERA)
            }
            .show()
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)

        imageFromGallery = uri
    }

    private fun openSenderApp() {
        val telegramIntent = Intent()

        telegramIntent.action = Intent.ACTION_SEND

        telegramIntent.type = "text/plain"

        telegramIntent.putExtra(
                Intent.EXTRA_TEXT,
                "${tvName.text}" +
                        "\n${tvSurname.text}" +
                        "\n${tvAge.text}"
            )

        telegramIntent.putExtra(Intent.EXTRA_STREAM, imageFromGallery)


        telegramIntent.setPackage("org.telegram.messenger")

        startActivity(telegramIntent)
    }

}