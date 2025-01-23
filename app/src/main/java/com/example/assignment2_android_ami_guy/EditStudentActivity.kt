package com.example.assignment2_ami_guy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {
    private lateinit var editStudentName: EditText
    private lateinit var editStudentId: EditText
    private lateinit var editStudentPhone: EditText
    private lateinit var editStudentAddress: EditText
    private lateinit var editStudentChecked: CheckBox
    private lateinit var saveStudentButton: Button
    private lateinit var deleteStudentButton: Button
    private lateinit var cancelEditButton: Button
    private lateinit var studentId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        editStudentName = findViewById(R.id.editStudentName)
        editStudentId = findViewById(R.id.editStudentId)
        editStudentPhone = findViewById(R.id.editStudentPhone)
        editStudentAddress = findViewById(R.id.editStudentAddress)
        editStudentChecked = findViewById(R.id.editStudentChecked)
        saveStudentButton = findViewById(R.id.saveStudentButton)
        deleteStudentButton = findViewById(R.id.deleteStudentButton)
        cancelEditButton = findViewById(R.id.cancelEditButton)

        studentId = intent.getStringExtra("STUDENT_ID") ?: ""

        val student = StudentRepository.getStudentById(studentId)
        student?.let {
            editStudentName.setText(it.name)
            editStudentId.setText(it.id)
            editStudentPhone.setText(it.phone)
            editStudentAddress.setText(it.address)
            editStudentChecked.isChecked = it.isChecked
        }

        saveStudentButton.setOnClickListener {
            val name = editStudentName.text.toString().trim()
            val id = editStudentId.text.toString().trim()
            val phone = editStudentPhone.text.toString().trim()
            val address = editStudentAddress.text.toString().trim()

            if (name.isEmpty() || id.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (id != studentId && StudentRepository.getStudentById(id) != null) {
                Toast.makeText(this, "Student with this ID already exists!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            student?.let {
                it.name = name
                it.id = id
                it.phone = phone
                it.address = address
                it.isChecked = editStudentChecked.isChecked
                StudentRepository.updateStudent(it)
                Toast.makeText(this, "Student updated successfully!", Toast.LENGTH_SHORT).show()
                navigateToMainActivity()
            }
        }

        deleteStudentButton.setOnClickListener {
            student?.let {
                StudentRepository.deleteStudent(it.id)
                Toast.makeText(this, "Student deleted successfully!", Toast.LENGTH_SHORT).show()
                navigateToMainActivity()
            }
        }

        cancelEditButton.setOnClickListener {
            Toast.makeText(this, "Edit canceled!", Toast.LENGTH_SHORT).show()
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}