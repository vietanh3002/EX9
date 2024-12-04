package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout

class EditStudentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_student, container, false)

        val nameInput = view.findViewById<TextInputLayout>(R.id.name)
        val mssvInput = view.findViewById<TextInputLayout>(R.id.mssv)
        val btnCancel = view.findViewById<Button>(R.id.button_cancel)
        val btnOk = view.findViewById<Button>(R.id.button_ok)

        val studentName = arguments?.getString("studentName") ?: ""
        val studentId = arguments?.getString("studentId") ?: ""

        nameInput.editText?.setText(studentName)
        mssvInput.editText?.setText(studentId)

        btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        btnOk.setOnClickListener {
            val updatedName = nameInput.editText?.text.toString()
            val updatedMssv = mssvInput.editText?.text.toString()

            val result = Bundle().apply {
                putString("updatedName", updatedName)
                putString("updatedMssv", updatedMssv)
                putInt("position", arguments?.getInt("position", -1) ?: -1)
            }
            findNavController().previousBackStackEntry?.savedStateHandle?.set("editStudent", result)
            findNavController().navigateUp()
        }

        return view
    }
} 