package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_TEXT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
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


private const val PERMISSION_REQUEST_CODE = 101

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private fun requestPermission() {

        val D = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.CAMERA
        )

        if (D) {
            //Вызов Rationale Dialog
            showRationaleDialog(this)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CODE
            )

        }


    }

    // По клику на кнопку Сделать фото запросите у пользователя Runtime Permission
    // на использование камеры. Обработайте следующие возможные сценарии:
    //
    //
    // 1. Пользователя выдал разрешение на использование камеры → отобразите в ImageView ресурс `R.drawable.cat`
    //         2. Пользователь не разрешил использовать камеру первый раз → ничего не делаем
    //  3. Пользователь еще раз запросил разрешение на использование камеры после отмены → покажите Rationale Dialog,
    //  и объясните зачем вам камера

    private fun onClick1(dialog: DialogInterface, which: Int) {
        //если пользователь выбрал пункт меню=0 (Сделать фото), то запрашиваем у пользователя разрешение на использование камеры
        if (which == 0) {

            //если пользователь дал разрешение на использование камеры, то отображаем в ImageView ресурс `R.drawable.cat`
            //если не разрешил, но еще разз запросил разрешение - показываем Rationale Dialog с объяснением зачем нужна камера и
            //кнопками - “Дать доступ” и “Отмена”. По клику на кнопку “Дать доступ” повторно запросите разрешение, по клику на кнопку “Отмена” закройте диалоговое окно

            //если проверитьРазрешение (контекст эта активити, название разрешения) == Разрешение предоставлено
            //строка проверяет, что разрешение уже было ранее предоставлено и у пользователя ничего не запрашивается
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                showImage()

                // иначe
                //запросить у пользователя разрешение на пермишион - использование камеры
            } else
                requestPermission()


        } else
            takePictureFromGallery()
    }

    private fun showRationaleDialog(context: Activity) {
        val show = MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.title))
            .setMessage(
                resources.getString(
                    R.string.supporting_text
                )
            )

            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                // Respond to negative button press
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                // Respond to positive button press
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_REQUEST_CODE
                )
            }
            .show()
    }

    private fun showSettingsDialog(context: Activity) {
        val show = MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.title))
            .setMessage(
                resources.getString(
                    R.string.settings_text
                )
            )

            .setPositiveButton(resources.getString(R.string.settings_text)) { dialog, which ->
                // Respond to positive button press
                openApplicationSettings()
            }
            .show()
    }

    private fun openApplicationSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        )
        startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE)
    }

    // По клику на кнопку “Выбрать фото” откройте экран выбора фото из галлереи,
    // после того как вы получите URI фотографии в `ActivityResultCallback` вызовите метод `populateImage`,
    // чтобы отобразить полученную фотографию в ImageView
    //
    //💡 Используйте готовый контракт из класса `ActivityResultContracts` для открытия пикера медиафайлов

    private fun showImage() {
        //отображаем в ImageView ресурс `R.drawable.cat`
        // вызываем getDrawable для получения изображения

        //переменной bd присвоить значение которое нужно получить из ресрсов (id ресурса) в пригодно виде для помещения в imageView, а именно в виде типа BitmapDrawable
        val bd = this.getResources().getDrawable(R.drawable.cat);

        //в переменную imageView  помещаем значение найденной функцией  findViewById "переводится как найти View посредством айди"
        // которой мы передаем в скобках айди нужного нам элемента imageView с нашего экрана ( активити)
        imageView = findViewById<ImageView>(R.id.imageview_photo)

        // у ранее найденного imageView вызвать метод setImageDrawable "переводится как Установить картинку Drawable" - этот метод назначет в ImageView указанную картинку,
        // которая должна иметь тип Drawable или производный тип от него. Ранее мы создали переменную bd с типом BitmapDrawable
        imageView.setImageDrawable(bd)
    }

    val openFillFormActivityLaunch =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult(),

            object : ActivityResultCallback<ActivityResult> {
                override fun onActivityResult(result: ActivityResult) {
                    if (result.resultCode == Activity.RESULT_OK) {
                        val intent = result.data
                        // Handle the Intent
                        val arguments = intent!!.extras
                        val name = arguments!!["name"].toString()
                        val surname = arguments!!["surname"].toString()
                        val age = arguments!!["age"].toString()

                        findViewById<TextView>(R.id.textview_name).text = name
                        findViewById<TextView>(R.id.textview_surname).text = surname
                        findViewById<TextView>(R.id.textview_age).text = age
                    }
                }

            }

        )


    private fun takePictureFromGallery() {
        getContent.launch("image/*")

    }

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? -> populateImage(uri!!)

        // Handle the returned Uri
    }

    lateinit var myUri : Uri


    private fun openFillFormActivity() {
        openFillFormActivityLaunch.launch(Intent(this, FillFormActivity::class.java))

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showImage()
        } else {

            val D = ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            )
            if (D) {

            } else showSettingsDialog(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById<ImageView>(R.id.imageview_photo)


        // В классе EditProfileActivity по клику на ImageView с id=imageview_photo

        imageView.setOnClickListener {

            //объявила переменную с именем items и сразу присвоила значение типа массив строк
            val items = arrayOf("Сделать фото", "Выбрать фото")

            //покажите Alert Dialog с выбором действия. Используйте реализацию диалога указанную
            //в примере, в качестве элементов массива добавьте элементы “Сделать фото” и “Выбрать фото”

            //создаю диалог
            MaterialAlertDialogBuilder(this)
                //установить заголовок (ресурсы.взять строку(номер строки))
                .setTitle(resources.getString(R.string.title))
                //установить элементы (список элементов, обработчик нажатия на элемент)
                .setItems(items, { dialog, which -> onClick1(dialog, which) })
                //показать
                .show()
        }

        var settingProfile = findViewById<Button>(R.id.Setting_Profile)
        settingProfile.setOnClickListener {
            openFillFormActivity()

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


    /** private fun requestPermission() {
    ActivityCompat.requestPermissions(
    activity,
    arrayOf(Manifest.permission.CAMERA),
    PERMISSION_REQUEST_CODE
    )
    }


     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        myUri = uri
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }


    private fun openSenderApp() {

        var photo = findViewById<ImageView>(R.id.imageview_photo)

        var name = findViewById<TextView>(R.id.textview_name).text
        var surname = findViewById<TextView>(R.id.textview_surname).text
        var age = findViewById<TextView>(R.id.textview_age).text
        var content =  "$name \n $surname \n $age"

        val i = Intent(Intent.ACTION_SEND)
        i.type = "image/jpeg"
        val intent = i.setPackage("org.telegram.messenger")


        i.putExtra(Intent.EXTRA_TEXT, content)
        i.putExtra(Intent.EXTRA_STREAM, myUri)
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)


        startActivity(i)
    }

    //  TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
}



