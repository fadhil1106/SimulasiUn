package com.nunukan.workshop.simulasiun

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import com.google.firebase.database.*

class ExamActivity : AppCompatActivity() {

    private lateinit var myRef: DatabaseReference
    private lateinit var btnNextQuestion: Button
    private lateinit var btnFinish: Button
    private lateinit var txtChoiceA:RadioButton
    private lateinit var txtChoiceB:RadioButton
    private lateinit var txtChoiceC:RadioButton
    private lateinit var txtChoiceD:RadioButton
    private var questionList: MutableList<QuestionModel> = mutableListOf()
    private var answerChoose: MutableList<String> = mutableListOf()
    private var subject:String = ""
    private var questionNumber: Int = 0
    private var score:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        btnNextQuestion = findViewById(R.id.btn_next_question)
        btnFinish = findViewById(R.id.btn_finish)

        //Mengambil Data Subject yang Dikirim Dari Activity Sebelumnya
        subject = intent.getStringExtra("subject")
        Log.e("Subject:Choose", subject)

        getQuestionList()
        btnNextQuestion.setOnClickListener {
            checkAnswer()
            showQuestion(questionNumber+1)
        }
        btnFinish.setOnClickListener {
            checkAnswer()
            countScore()
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("score", score)
            intent.putExtra("totalQuestion", questionList.size)
            startActivity(intent)
            finish()
        }
    }

    //Mengambil List Pertanyaan Dalam Database
    private fun getQuestionList(){
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        myRef = FirebaseDatabase.getInstance().reference

        val questionListListener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase:Error", error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot){
                snapshot.children.mapNotNullTo(questionList){
                    it.getValue<QuestionModel>(QuestionModel::class.java)
                }
                questionList.shuffle()
                questionNumber = 0
                showQuestion(questionNumber)
                progressBar.visibility = View.GONE
            }
        }
        myRef.child("questionList/$subject").addListenerForSingleValueEvent(questionListListener)
    }

    @SuppressLint("SetTextI18n")
    private fun showQuestion(questionNumber:Int){
        isLastQuestion()

        val txtQuestionNumber = findViewById<TextView>(R.id.txt_question_number)
        val txtQuestion = findViewById<TextView>(R.id.txt_question)
        txtChoiceA = findViewById(R.id.choice_a)
        txtChoiceB = findViewById(R.id.choice_b)
        txtChoiceC = findViewById(R.id.choice_c)
        txtChoiceD = findViewById(R.id.choice_d)

        txtQuestionNumber.text = "Soal ${questionNumber+1}"
        txtQuestion.text = questionList[questionNumber].question
        txtChoiceA.text = questionList[questionNumber].choiceA
        txtChoiceB.text = questionList[questionNumber].choiceB
        txtChoiceC.text = questionList[questionNumber].choiceC
        txtChoiceD.text = questionList[questionNumber].choiceD
    }

    private fun isLastQuestion(){
        if (questionNumber == questionList.size-1){
            btnNextQuestion.visibility = View.GONE
            btnFinish.visibility = View.VISIBLE
        }
    }

    private fun checkAnswer(){
        when {
            txtChoiceA.isChecked -> answerChoose.add("${txtChoiceA.text}")
            txtChoiceB.isChecked -> answerChoose.add("${txtChoiceB.text}")
            txtChoiceC.isChecked -> answerChoose.add("${txtChoiceC.text}")
            txtChoiceD.isChecked -> answerChoose.add("${txtChoiceD.text}")
        }
    }

    private fun countScore(){
        Log.e("Question List", answerChoose.toString())
        for (index in questionList.indices){
            if(questionList[index].answer == answerChoose[index]){
                score++
            }
        }
    }
}