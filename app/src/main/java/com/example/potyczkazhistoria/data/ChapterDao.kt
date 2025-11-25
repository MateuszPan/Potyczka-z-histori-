package com.example.potyczkazhistoria.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChapterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(chapters: List<ChapterEntity>)

    @Query("SELECT * FROM chapters WHERE epochId = :epochId")
    suspend fun getChaptersForEpoch(epochId: Int): List<ChapterEntity>

    @Query("DELETE FROM chapters")
    suspend fun deleteAll()
}