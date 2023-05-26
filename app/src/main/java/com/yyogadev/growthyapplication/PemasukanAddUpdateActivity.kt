package com.yyogadev.growthyapplication

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.yyogadev.growthyapplication.databinding.ActivityPemasukanAddUpdateBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PemasukanAddUpdateActivity : AppCompatActivity() {
    private var isEdit = false
//    private var note: Note? = null
    private var position: Int = 0
//    private lateinit var noteHelper: NoteHelper

    private lateinit var binding: ActivityPemasukanAddUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPemasukanAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        note = intent.getParcelableExtra(EXTRA_NOTE)
//        if (note != null) {
//            position = intent.getIntExtra(EXTRA_POSITION, 0)
//            isEdit = true
//        } else {
//            note = Note()
//        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"
//            note?.let {
//                binding.edtTitle.setText(it.title)
//                binding.edtDescription.setText(it.description)
//            }
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.txtSubmit.text = btnTitle
    }

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }


    fun showDatePicker(view: View) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)

                val dateEditText = findViewById<EditText>(R.id.dateEditText)
                val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val selectedDateString = dateFormat.format(selectedDate.time)

                dateEditText.setText(selectedDateString)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}