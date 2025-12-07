package com.example.potyczkazhistoria.data
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "chapters",
    foreignKeys = [
        ForeignKey(
            entity = EpochEntity::class,
            parentColumns = ["id"],
            childColumns = ["epochId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ChapterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val epochId: Int
)
