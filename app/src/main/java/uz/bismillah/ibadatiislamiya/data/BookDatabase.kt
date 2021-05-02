package uz.bismillah.ibadatiislamiya.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.bismillah.ibadatiislamiya.data.dao.UnitDao

@Database(entities = [Unit::class], version = 1)
abstract class BookDatabase : RoomDatabase() {
    companion object {
        private lateinit var INSTANCE: BookDatabase

        fun getInstance(context: Context) : BookDatabase =
            Room.databaseBuilder(
                context,
                BookDatabase::class.java,
                "ibadati_islamiya.db3"
            )
                .createFromAsset("ibadati_islamiya.db3")
                .build()
    }

    abstract fun unitDao() : UnitDao
}