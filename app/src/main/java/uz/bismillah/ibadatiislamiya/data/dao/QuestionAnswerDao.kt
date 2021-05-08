package uz.bismillah.ibadatiislamiya.data.dao

import androidx.room.Dao
import androidx.room.Query
import uz.bismillah.ibadatiislamiya.data.model.QuestionAnswer

@Dao
interface QuestionAnswerDao {
    @Query("SELECT * FROM question_answer")
    fun getAllQuestions() : List<QuestionAnswer>

    @Query("SELECT * FROM question_answer WHERE topic_id IN (:topicIds)")
    fun getAllByTopics(topicIds: IntArray) : List<QuestionAnswer>

    @Query("SELECT * FROM question_answer WHERE topic_id = (:topicId)")
    fun getByTopic(topicId: Int) : List<QuestionAnswer>
}