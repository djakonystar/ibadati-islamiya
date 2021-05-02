package uz.bismillah.ibadatiislamiya.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
data class Answer(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "answer")
    val answer: String,

    @ColumnInfo(name = "question_id")
    val questionId: Int,

    @ColumnInfo(name = "topic_id")
    val topicId: Int,

    @ColumnInfo(name = "unit_id")
    val unitId: Int
)
