package uz.bismillah.ibadatiislamiya.data.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface UnitDao {
    @Query("SELECT * FROM units")
    fun getAllUnits(): List<Unit>
}