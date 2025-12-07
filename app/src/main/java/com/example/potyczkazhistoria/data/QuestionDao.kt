package com.example.potyczkazhistoria.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>)
    @Query("SELECT * FROM questions WHERE chapterId = :chapterId AND difficulty = :difficulty")
    suspend fun getQuestionsByChapterAndDifficulty(chapterId: Int, difficulty: String): List<QuestionEntity>
    @Query("SELECT * FROM questions")
    suspend fun getAllQuestions(): List<QuestionEntity>
    @Query("DELETE FROM questions")
    suspend fun deleteAll()
}