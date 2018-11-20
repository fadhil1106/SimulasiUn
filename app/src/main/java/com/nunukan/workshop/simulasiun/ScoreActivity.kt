package com.nunukan.workshop.simulasiun

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val totalQuestion = intent.getIntExtra("totalQuestion",0)
        val score = intent.getIntExtra("score",0)

        val txtScore = findViewById<TextView>(R.id.txt_score)
        val txtComment = findViewById<TextView>(R.id.txt_comment)
        val btnHome = findViewById<Button>(R.id.btn_home)

        txtScore.text = "$score / $totalQuestion"
        when {
            score < totalQuestion * (50f/100f) -> txtComment.text = "Kamu Harus Belajar Lebih Giat Lagi"
            score < totalQuestion * (80f/100f) -> txtComment.text = "Nilai Kamu Sudah Cukup Baik, Pertahankan"
            else -> txtComment.text = "Kamu Luar Biasaaa...."
        }

        btnHome.setOnClickListener {finish()}
    }
}
