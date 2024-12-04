package vn.edu.hust.studentman

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.TextView

class StudentAdapter(
    context: Context,
    private val students: MutableList<StudentModel>,
    private val listener: OnItemClickListener
) : ArrayAdapter<StudentModel>(context, R.layout.layout_student_item, students) {

    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.layout_student_item, parent, false)
        val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
        val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)

        val student = students[position]
        textStudentName.text = student.studentName
        textStudentId.text = student.studentId


        itemView.setOnLongClickListener {
            val contextMenu = PopupMenu(itemView.context, itemView)
            contextMenu.menu.add("Chỉnh sửa").setOnMenuItemClickListener {
                listener.onEditClick(position)
                true
            }
            contextMenu.menu.add("Xóa").setOnMenuItemClickListener {
                listener.onDeleteClick(position)
                true
            }
            contextMenu.show()
            true
        }

        return itemView
    }
}