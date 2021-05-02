package uz.bismillah.ibadatiislamiya.data.dao

import androidx.room.Dao
import androidx.room.Query
import uz.bismillah.ibadatiislamiya.data.model.Topic

@Dao
interface TopicDao {
    @Query("SELECT * FROM topics")
    fun getAllTopics() : List<Topic>

    @Query("SELECT * FROM topics WHERE unit_id = 1")
    fun getTopicsOfFirstUnit() : List<Topic>

    @Query("SELECT * FROM topics WHERE unit_id = 2")
    fun getTopicsOfSecondUnit() : List<Topic>

    @Query("SELECT * FROM topics WHERE unit_id = 3")
    fun getTopicsOfThirdUnit() : List<Topic>

    @Query("SELECT * FROM topics WHERE unit_id = 4")
    fun getTopicsOfFourthUnit() : List<Topic>

    @Query("SELECT * FROM topics WHERE unit_id = 5")
    fun getTopicsOfFifthUnit() : List<Topic>
}