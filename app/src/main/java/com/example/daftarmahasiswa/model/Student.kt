package com.example.daftarmahasiswa.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val nim: String
)

