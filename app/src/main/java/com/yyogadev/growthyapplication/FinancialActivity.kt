package com.yyogadev.growthyapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class FinancialActivity : AppCompatActivity(),  View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_financial)

        val btnPemasukan: Button = findViewById(R.id.btn_pemasukan)
        val btnPengeluaran: Button = findViewById(R.id.btn_pengeluaran)
        btnPemasukan.setOnClickListener(this)
        btnPengeluaran.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.btn_pemasukan) {
                val intent = Intent(this, PemasukanAddUpdateActivity::class.java)

                // Add any extra data to the intent if needed
    //            intent.putExtra("key", "value")

                // Start the second activity
                startActivity(intent)
            }
            if (v.id == R.id.btn_pengeluaran) {

                val intent = Intent(this, PengeluaranAddUpdateActivity::class.java)

                // Add any extra data to the intent if needed
                //            intent.putExtra("key", "value")

                // Start the second activity
                startActivity(intent)
            }

        }
    }
}