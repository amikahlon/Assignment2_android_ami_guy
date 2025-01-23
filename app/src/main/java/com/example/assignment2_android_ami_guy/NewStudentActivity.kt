package com.example.assignment2_android_ami_guy

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NewStudentActivity : AppCompatActivity() {
    private lateinit var newStudentName: EditText
    private lateinit var newStudentId: EditText
    private lateinit var newStudentPhone: EditText
    private lateinit var newStudentAddress: EditText
    private lateinit var newStudentChecked: CheckBox
    private lateinit var saveStudentButton: Button
    private lateinit var cancelStudentButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)

        newStudentName = findViewById(R.id.newStudentName)
        newStudentId = findViewById(R.id.newStudentId)
        newStudentPhone = findViewById(R.id.newStudentPhone)
        newStudentAddress = findViewById(R.id.newStudentAddress)
        newStudentChecked = findViewById(R.id.newStudentChecked)
        saveStudentButton = findViewById(R.id.saveStudentButton)
        cancelStudentButton = findViewById(R.id.cancelStudentButton)

        saveStudentButton.setOnClickListener {
            val name = newStudentName.text.toString().trim()
            val id = newStudentId.text.toString().trim()
            val phone = newStudentPhone.text.toString().trim()
            val address = newStudentAddress.text.toString().trim()
            val isChecked = newStudentChecked.isChecked

            if (name.isEmpty() || id.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (StudentRepository.getStudentById(id) != null) {
                Toast.makeText(this, "Student with this ID already exists!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newStudent = Student(id, name, phone, address, isChecked)
            StudentRepository.addStudent(newStudent)
            Toast.makeText(this, "Student added successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }

        cancelStudentButton.setOnClickListener {
            Toast.makeText(this, "Action canceled!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}