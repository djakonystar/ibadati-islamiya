package uz.bismillah.ibadatiislamiya.ui.search

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_question_answer.view.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.model.QuestionAnswer

class SearchResultListAdapter : RecyclerView.Adapter<SearchResultListAdapter.SearchResultListViewHolder>() {
    var models = listOf<QuestionAnswer>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onQuestionFavIconClick: (questionAnswer: QuestionAnswer) -> Unit = {}
    fun setOnQuestionFavIconClickListener(onQuestionFavIconClick: (questionAnswer : QuestionAnswer) -> Unit) {
        this.onQuestionFavIconClick = onQuestionFavIconClick
    }

    private var onQuestionCopyIconClick: (question: String, answer: String) -> Unit = { question, answer -> }
    fun setOnQuestionCopyIconClickListener(onQuestionCopyIconClick: (question: String, answer: String) -> Unit) {
        this.onQuestionCopyIconClick = onQuestionCopyIconClick
    }

    private var onQuestionShareIconClick: (question: String, answer: String) -> Unit = { question, answer -> }
    fun setOnQuestionShareIconClickListener(onQuestionShareIconClick: (question: String, answer: String) -> Unit) {
        this.onQuestionShareIconClick = onQuestionShareIconClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_question_answer, parent, false)
        return SearchResultListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchResultListViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount(): Int = models.size

    inner class SearchResultListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populateModel(questionAnswer: QuestionAnswer) {
            itemView.questionTextView.text = questionAnswer.question
            itemView.answerTextView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(questionAnswer.answer, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(questionAnswer.answer)
            }

            var isFavorite = questionAnswer.isFavorite == 1

            if (isFavorite) {
                itemView.addToBookmark.progress = 0.44f
            }
            itemView.copy.progress = 0.67f
            itemView.share.progress = 0.67f

            itemView.addToBookmark.setOnClickListener {
                if (!isFavorite) {
                    itemView.addToBookmark.speed = 1f
                    itemView.addToBookmark.playAnimation()
                } else {
                    itemView.addToBookmark.speed = -1.7f
                    itemView.addToBookmark.setMinAndMaxFrame(0, 22)
                    itemView.addToBookmark.playAnimation()
                }
                isFavorite = !isFavorite
                onQuestionFavIconClick.invoke(questionAnswer)
            }

            itemView.copy.setOnClickListener {
                itemView.copy.setMinAndMaxFrame(0, 80)
                itemView.copy.speed = 2.6f
                itemView.copy.playAnimation()
                onQuestionCopyIconClick.invoke(questionAnswer.question, questionAnswer.answer)
            }

            itemView.share.setOnClickListener {
                itemView.share.setMinAndMaxFrame(0, 40)
                itemView.share.speed = 1.5f
                itemView.share.playAnimation()
                onQuestionShareIconClick.invoke(questionAnswer.question, questionAnswer.answer)
            }
        }
    }
}