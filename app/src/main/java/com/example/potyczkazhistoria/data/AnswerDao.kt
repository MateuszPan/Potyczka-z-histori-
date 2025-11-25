package com.example.potyczkazhistoria.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnswerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(answers: List<AnswerEntity>)

    @Query("SELECT * FROM answers WHERE questionId = :questionId")
    suspend fun getAnswersByQuestionId(questionId: Int): List<AnswerEntity>

    @Query("SELECT * FROM answers WHERE questionId = :questionId")
    suspend fun getAnswersForQuestion(questionId: Int): List<AnswerEntity>

    @Query("DELETE FROM answers")
    suspend fun deleteAll()
}
