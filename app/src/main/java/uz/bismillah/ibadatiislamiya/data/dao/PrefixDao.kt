package uz.bismillah.ibadatiislamiya.data.dao

import androidx.room.Dao
import androidx.room.Query
import uz.bismillah.ibadatiislamiya.data.model.Prefix

@Dao
interface PrefixDao {
    @Query("SELECT * FROM prefixes")
    fun getAllPrefixes() : List<Prefix>

    @Query("SELECT * FROM prefixes WHERE topic_id = (:topicId)")
    fun getPrefixByTopic(topicId: Int) : Prefix
}