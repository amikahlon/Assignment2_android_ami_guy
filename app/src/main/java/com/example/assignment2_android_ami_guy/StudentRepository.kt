package com.example.assignment2_android_ami_guy

object StudentRepository {
    val students = mutableListOf<Student>()

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun updateStudent(student: Student) {
        val index = students.indexOfFirst { it.id == student.id }
        if (index != -1) {
            students[index] = student
        }
    }

    fun deleteStudent(id: String) {
        students.removeAll { it.id == id }
    }

    fun getStudentById(id: String): Student? {
        return students.find { it.id == id }
    }
}
