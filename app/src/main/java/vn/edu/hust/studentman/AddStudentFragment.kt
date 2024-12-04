package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout

class AddStudentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_student, container, false)

        val nameInput = view.findViewById<TextInputLayout>(R.id.name)
        val mssvInput = view.findViewById<TextInputLayout>(R.id.mssv)
        val btnCancel = view.findViewById<Button>(R.id.button_cancel)
        val btnOk = view.findViewById<Button>(R.id.button_ok)

        btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        btnOk.setOnClickListener {
            val studentName = nameInput.editText?.text.toString()
            val studentId = mssvInput.editText?.text.toString()

            val result = Bundle().apply {
                putString("studentName", studentName)
                putString("studentId", studentId)
            }
            findNavController().previousBackStackEntry?.savedStateHandle?.set("addStudent", result)
            findNavController().navigateUp()
        }

        return view
    }
} 