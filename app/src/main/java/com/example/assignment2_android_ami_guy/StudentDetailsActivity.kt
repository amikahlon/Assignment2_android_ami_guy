package com.example.assignment2_ami_guy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StudentDetailsActivity : AppCompatActivity() {
    private lateinit var studentDetailsImage: ImageView
    private lateinit var studentDetailsName: TextView
    private lateinit var studentDetailsId: TextView
    private lateinit var studentDetailsPhone: TextView
    private lateinit var studentDetailsAddress: TextView
    private lateinit var studentDetailsChecked: CheckBox
    private lateinit var editStudentButton: Button
    private lateinit var studentId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        studentDetailsImage = findViewById(R.id.studentDetailsImage)
        studentDetailsName = findViewById(R.id.studentDetailsName)
        studentDetailsId = findViewById(R.id.studentDetailsId)
        studentDetailsPhone = findViewById(R.id.studentDetailsPhone)
        studentDetailsAddress = findViewById(R.id.studentDetailsAddress)
        studentDetailsChecked = findViewById(R.id.studentDetailsChecked)
        editStudentButton = findViewById(R.id.editStudentButton)

        studentId = intent.getStringExtra("STUDENT_ID") ?: ""

        val student = StudentRepository.getStudentById(studentId)
        student?.let {
            studentDetailsImage.setImageResource(R.drawable.ic_student) // Static image
            studentDetailsName.text = "Name: ${it.name}"
            studentDetailsId.text = "ID: ${it.id}"
            studentDetailsPhone.text = "Phone: ${it.phone}"
            studentDetailsAddress.text = "Address: ${it.address}"
            studentDetailsChecked.isChecked = it.isChecked
            studentDetailsChecked.text = if (it.isChecked) "Checked" else "Not Checked"

            studentDetailsChecked.setOnCheckedChangeListener { _, isChecked ->
                it.isChecked = isChecked
                studentDetailsChecked.text = if (isChecked) "Checked" else "Not Checked"
                StudentRepository.updateStudent(it)
            }
        }

        editStudentButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("STUDENT_ID", studentId)
            startActivity(intent)
        }
    }
}