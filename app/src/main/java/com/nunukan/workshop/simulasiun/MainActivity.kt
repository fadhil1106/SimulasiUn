package com.nunukan.workshop.simulasiun

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnBiology = findViewById<Button>(R.id.btn_biology)
        val btnPhysics = findViewById<Button>(R.id.btn_physics)
        val btnMath = findViewById<Button>(R.id.btn_math)
        val btnIndonesia = findViewById<Button>(R.id.btn_indonesia)
        val btnEnglish = findViewById<Button>(R.id.btn_english)

        btnBiology.setOnClickListener {intent("biology")}

        btnPhysics.setOnClickListener {intent("physics")}

        btnMath.setOnClickListener {intent("math")}

        btnIndonesia.setOnClickListener {intent("indonesia")}

        btnEnglish.setOnClickListener {intent("english")}
    }

    private fun intent(subject: String) {
        val intent = Intent(this, ExamActivity::class.java)
        intent.putExtra("subject", subject)
        startActivity(intent)
    }
}
