package uz.bismillah.ibadatiislamiya.data.dao

import androidx.room.Dao
import androidx.room.Query
import uz.bismillah.ibadatiislamiya.data.model.Topic

@Dao
interface TopicDao {
    @Query("SELECT * FROM topics")
    fun getAllTopics() : List<Topic>

    @Query("SELECT * FROM topics WHERE unit_id = :unitId")
    fun getTopicsByUnit(unitId: Int) : List<Topic>
}