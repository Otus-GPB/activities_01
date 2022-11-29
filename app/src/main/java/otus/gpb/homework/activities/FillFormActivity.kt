package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.res.TypedArrayUtils.getText


class FillFormActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        var applyData = findViewById<Button>(R.id.apply_Data)
        applyData.setOnClickListener {
            returnData()

        }
    }

    // указываем первым параметром ключ, а второе значение
    // по ключу мы будем получать значение с Intent
    // надо сделать:
    // работает неправильно - не передаются нужные (введенные, вместо них передается значение из путэкстра,
    // а именно константы : name, surname, age))
    // значения из текстовых полей),
    // нужно, чтобы знаения брались из поля edittext с id name, surname и age, после того как их ввел пользователь
    // и передавались в intent с помощью пут екстра

    private fun returnData() {
        var nameEditText = findViewById<TextView>(R.id.name)
        var surnameEditText = findViewById<TextView>(R.id.surname)
        var ageEditText = findViewById<TextView>(R.id.age)
        val intent = Intent(this, EditProfileActivity::class.java)
        intent.putExtra("name", nameEditText.getText().toString())
        intent.putExtra("surname", surnameEditText.getText().toString())
        intent.putExtra("age", ageEditText.getText().toString())
        setResult(RESULT_OK, intent)
        finish()
    }
}
