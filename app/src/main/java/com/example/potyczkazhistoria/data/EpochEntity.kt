package com.example.potyczkazhistoria.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "epochs")
data class EpochEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
