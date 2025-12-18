package com.example.daftarmahasiswa

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daftarmahasiswa.model.AppDatabase
import com.example.daftarmahasiswa.model.Student
import com.example.daftarmahasiswa.model.StudentAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var studentAdapter: StudentAdapter

    private var selectedStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNama: EditText = findViewById(R.id.etNama)
        val etNim: EditText = findViewById(R.id.etNim)
        val btnSave: Button = findViewById(R.id.btnSave)
        val rvStudent: RecyclerView = findViewById(R.id.rvStudent)

        db = AppDatabase.getDatabase(this)

        studentAdapter = StudentAdapter(
            arrayListOf(),
            onDeleteClick = { student ->
                lifecycleScope.launch {
                    db.studentDao().deleteStudent(student)
                    loadData()
                }
            },
            onItemClick = { student ->
                selectedStudent = student
                etNama.setText(student.nama)
                etNim.setText(student.nim)
                btnSave.text = "Update"
            }
        )

        rvStudent.layoutManager = LinearLayoutManager(this)
        rvStudent.adapter = studentAdapter

        loadData()

        btnSave.setOnClickListener {
            val nama = etNama.text.toString()
            val nim = etNim.text.toString()

            lifecycleScope.launch {
                if (selectedStudent == null) {
                    db.studentDao().insertStudent(
                        Student(nama = nama, nim = nim)
                    )
                } else {
                    db.studentDao().updateStudent(
                        Student(
                            id = selectedStudent!!.id,
                            nama = nama,
                            nim = nim
                        )
                    )
                    selectedStudent = null
                    btnSave.text = "Simpan"
                }

                etNama.text.clear()
                etNim.text.clear()
                loadData()
            }
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            val students = db.studentDao().getAllStudents()
            studentAdapter.setData(students)
        }
    }
}
