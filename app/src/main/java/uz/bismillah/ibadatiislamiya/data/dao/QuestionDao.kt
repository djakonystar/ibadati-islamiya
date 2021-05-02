package uz.bismillah.ibadatiislamiya.data.dao

import androidx.room.Dao
import androidx.room.Query
import uz.bismillah.ibadatiislamiya.data.model.Question

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions")
    fun getAllQuestions() : List<Question>

    @Query("SELECT * FROM questions WHERE topic_id IN (:topicIds)")
    fun loadAllByTopics(topicIds: IntArray) : List<Question>

    @Query("SELECT * FROM questions WHERE topic_id = (:topicId)")
    fun loadByTopic(topicId: Int) : Question
}