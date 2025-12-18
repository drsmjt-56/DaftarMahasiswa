package com.example.daftarmahasiswa.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {

    @Insert
    suspend fun insertStudent(student: Student)

    @Query("SELECT * FROM student_table ORDER BY nama ASC")
    suspend fun getAllStudents(): List<Student>

    @Delete
    suspend fun deleteStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)
}
