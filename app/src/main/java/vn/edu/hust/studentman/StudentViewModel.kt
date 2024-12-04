package vn.edu.hust.studentman

import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {
    private val students = mutableListOf<StudentModel>()

    fun addStudent(student: StudentModel) {
        students.add(student)
    }

    fun updateStudent(updatedName: String, updatedMssv: String) {
        // Cập nhật sinh viên trong danh sách
    }

    fun getStudents(): List<StudentModel> {
        return students
    }
} 