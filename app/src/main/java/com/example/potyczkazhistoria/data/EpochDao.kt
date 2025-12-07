package com.example.potyczkazhistoria.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EpochDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(epochs: List<EpochEntity>)
    @Query("SELECT * FROM epochs")
    suspend fun getAllEpochs(): List<EpochEntity>
    @Query("DELETE FROM epochs")
    suspend fun deleteAll()
}
