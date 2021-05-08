package uz.bismillah.ibadatiislamiya.ui.questionanswer

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_prefix.view.*
import kotlinx.android.synthetic.main.item_question_answer.view.*
import kotlinx.android.synthetic.main.item_question_answer.view.answerTextView
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BaseModelQAPrefix
import uz.bismillah.ibadatiislamiya.data.model.Prefix
import uz.bismillah.ibadatiislamiya.data.model.QuestionAnswer

class QuestionAnswerListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var models = listOf<BaseModelQAPrefix>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == BaseModelQAPrefix.PREFIX) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_prefix, parent, false)
            PrefixViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_question_answer, parent, false)
            QuestionAnswerListViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (models[position].type == BaseModelQAPrefix.PREFIX) {
            (holder as PrefixViewHolder).populateModel(models[position] as Prefix)
        } else {
            (holder as QuestionAnswerListViewHolder).populateModel(models[position] as QuestionAnswer)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return models[position].type
    }

    override fun getItemCount(): Int = models.size

    inner class QuestionAnswerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populateModel(questionAnswer: QuestionAnswer) {
            itemView.questionTextView.text = questionAnswer.question
            itemView.answerTextView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(questionAnswer.answer, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(questionAnswer.answer)
            }
        }
    }

    inner class PrefixViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populateModel(prefix: Prefix) {
            itemView.answerTextView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(prefix.text, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(prefix.text)
            }
        }
    }
}