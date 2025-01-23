package com.example.assignment2_ami_guy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var studentsRecyclerView: RecyclerView
    private lateinit var addStudentButton: FloatingActionButton
    private lateinit var adapter: StudentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentsRecyclerView = findViewById(R.id.studentsRecyclerView)
        addStudentButton = findViewById(R.id.addStudentButton)

        studentsRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentsAdapter(StudentRepository.students) { student ->
            val intent = Intent(this, StudentDetailsActivity::class.java)
            intent.putExtra("STUDENT_ID", student.id)
            startActivity(intent)
        }
        studentsRecyclerView.adapter = adapter

        addStudentButton.setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    inner class StudentsAdapter(
        private val students: List<Student>,
        private val onClick: (Student) -> Unit
    ) : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

        inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val studentImage: ImageView = itemView.findViewById(R.id.studentImage)
            val nameTextView: TextView = itemView.findViewById(R.id.studentName)
            val idTextView: TextView = itemView.findViewById(R.id.studentId)
            val checkBox: CheckBox = itemView.findViewById(R.id.studentCheckBox)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
            return StudentViewHolder(view)
        }

        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
            val student = students[position]
            holder.studentImage.setImageResource(R.drawable.ic_student)
            holder.nameTextView.text = student.name
            holder.idTextView.text = "ID: ${student.id}"
            holder.checkBox.isChecked = student.isChecked

            holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
                student.isChecked = isChecked
                StudentRepository.updateStudent(student)
            }

            holder.itemView.setOnClickListener {
                onClick(student)
            }
        }

        override fun getItemCount() = students.size
    }
}