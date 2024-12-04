package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

class StudentListFragment : Fragment() {
    private lateinit var studentAdapter: StudentAdapter
    private val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)

            studentAdapter = StudentAdapter(requireContext(), students, object : StudentAdapter.OnItemClickListener {
            override fun onEditClick(position: Int) {
                editStudent(position) // Gọi hàm chỉnh sửa với launcher
            }

            override fun onDeleteClick(position: Int) {
                deleteStudent(position)
            }
        })
        val listView = view.findViewById<ListView>(R.id.list_view_students)
        listView.adapter = studentAdapter

        // Thiết lập sự kiện nhấp vào nút thêm sinh viên
        view.findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            findNavController().navigate(R.id.addStudentFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>("addStudent")
            ?.observe(viewLifecycleOwner) { result ->
                val studentName = result.getString("studentName")
                val studentId = result.getString("studentId")

                if (!studentName.isNullOrEmpty() && !studentId.isNullOrEmpty()) {
                    addStudent(StudentModel(studentName, studentId))
                }
                navController.currentBackStackEntry?.savedStateHandle?.remove<Bundle>("addStudent")
            }
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>("editStudent")
            ?.observe(viewLifecycleOwner) { result ->
                val updatedName = result.getString("updatedName")
                val updatedMssv = result.getString("updatedMssv")
                val position = result.getInt("position", -1)

                if (!updatedName.isNullOrEmpty() && !updatedMssv.isNullOrEmpty() && position != -1) {
                    students[position] = StudentModel(updatedName, updatedMssv)
                    studentAdapter.notifyDataSetChanged()
                }
                navController.currentBackStackEntry?.savedStateHandle?.remove<Bundle>("editStudent")
            }
    }

    private fun addStudent(student: StudentModel) {
        students.add(student)
        studentAdapter.notifyDataSetChanged() // Cập nhật ListView
        Snackbar.make(requireView(), "Đã thêm sinh viên ${student.studentName}", Snackbar.LENGTH_SHORT).show()
    }

    private fun deleteStudent(position: Int) {
        if (position !in students.indices) {
            return
        }
        val deletedStudent = students[position]
        students.removeAt(position)
        studentAdapter.notifyDataSetChanged()

        view?.let {
            Snackbar.make(
                it.findViewById(R.id.list_view_students),  // Sử dụng ListView làm view gốc
                "Đã xóa sinh viên ${deletedStudent.studentName}",
                Snackbar.LENGTH_LONG
            ).setAction("Hoàn tác") {
                // Thêm lại sinh viên vào vị trí cũ
                students.add(position, deletedStudent)
                studentAdapter.notifyDataSetChanged()
            }.show()
        }
    }

    private fun editStudent(position: Int) {
        val student = students[position]
        val bundle = Bundle().apply {
            putString("studentName", student.studentName)
            putString("studentId", student.studentId)
            putInt("position", position)
        }
        findNavController().navigate(R.id.editStudentFragment, bundle)
    }
} 