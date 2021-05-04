package uz.bismillah.ibadatiislamiya.data.dao

import androidx.room.Dao
import androidx.room.Query
import uz.bismillah.ibadatiislamiya.data.model.Answer

@Dao
interface AnswerDao {
    @Query("SELECT * FROM answers")
    fun getAllAnswers() : List<Answer>

    @Query("SELECT * FROM answers WHERE question_id IN (:questionIds)")
    fun getAllByQuestions(questionIds: IntArray) : List<Answer>

    @Query("SELECT * FROM answers WHERE question_id = (:questionId)")
    fun getByQuestion(questionId: Int) : Answer

    @Query("SELECT * FROM answers WHERE topic_id = 0")
    fun getAllDuas() : List<Answer>
}