package uz.bismillah.ibadatiislamiya.ui.questionanswer

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_question_answer.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BaseModelQAPrefix
import uz.bismillah.ibadatiislamiya.data.BookDatabase
import uz.bismillah.ibadatiislamiya.data.dao.PrefixDao
import uz.bismillah.ibadatiislamiya.data.dao.QuestionAnswerDao
import uz.bismillah.ibadatiislamiya.data.dao.TopicDao
import uz.bismillah.ibadatiislamiya.data.model.Prefix
import uz.bismillah.ibadatiislamiya.data.model.QuestionAnswer
import uz.bismillah.ibadatiislamiya.ui.topic.TopicFragment

class QuestionAnswerFragment : Fragment(R.layout.fragment_question_answer) {
    private val adapter = QuestionAnswerListAdapter()
    private lateinit var questionAnswerDao: QuestionAnswerDao
    private lateinit var prefixDao: PrefixDao
    private lateinit var topicDao: TopicDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionAnswerToolBar.title = arguments?.getString(TopicFragment.TOPIC_NAME)
        questionAnswerToolBar.setNavigationIcon(R.drawable.ic_back_24)
        questionAnswerToolBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        questionAnswerRecyclerView.adapter = adapter
        questionAnswerDao = BookDatabase.getInstance(requireContext()).questionAnswerDao()
        prefixDao = BookDatabase.getInstance(requireContext()).prefixDao()
        topicDao = BookDatabase.getInstance(requireContext()).topicDao()
        setData(arguments?.getInt(TopicFragment.TOPIC_ID) ?: 1)

        questionAnswerToolBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.textSizeSettings) {
                val inflater = requireContext().getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView = inflater.inflate(R.layout.popup_textsize, null)

                val width = LinearLayout.LayoutParams.WRAP_CONTENT
                val height = LinearLayout.LayoutParams.WRAP_CONTENT
                val focusable = true
                val popupWindow = PopupWindow(popupView, width, height, focusable)
                popupWindow.showAsDropDown(questionAnswerAppBar,0, 10, Gravity.END)

                true
            } else {
                false
            }
        }

        adapter.setOnQuestionFavIconClickListener {
            setFavoriteQuestion(it)
        }

        adapter.setOnQuestionCopyIconClickListener { question, answer ->
            val answer1 = answer.replace("<b>", "")
            val answer2 = answer1.replace("</b>", "")
            val answer3 = answer2.replace("<p>", "\n")
            val answerFinal = answer3.replace("</p>", "")
            val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(question, "$question\n\n$answerFinal\n\n\n\"Ибадати Исламия\" китабынан")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Табыслы нусқаланды!", Toast.LENGTH_SHORT).show()
        }

        adapter.setOnQuestionShareIconClickListener { question, answer ->
            val answer1 = answer.replace("<b>", "")
            val answer2 = answer1.replace("</b>", "")
            val answer3 = answer2.replace("<p>", "\n")
            val answerFinal = answer3.replace("</p>", "")

            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, "$question\n" +
                        "\n" +
                        "$answerFinal\n" +
                        "\n" +
                        "\n" +
                        "\"Ибадати Исламия\" китабынан")
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }

        adapter.setOnPrefixFavIconClickListener {
            setFavoritePrefix(it)
        }

        adapter.setOnPrefixCopyIconClickListener {
            val prefix1 = it.replace("<b>", "")
            val prefix2 = prefix1.replace("</b>", "")
            val prefix3 = prefix2.replace("<p>", "\n")
            val prefixFinal = prefix3.replace("</p>", "")
            val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", "$prefixFinal\n\n\n\"Ибадати Исламия\" китабынан")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Табыслы нусқаланды!", Toast.LENGTH_SHORT).show()
        }

        adapter.setOnPrefixShareIconClickListener {
            val prefix1 = it.replace("<b>", "")
            val prefix2 = prefix1.replace("</b>", "")
            val prefix3 = prefix2.replace("<p>", "\n")
            val prefixFinal = prefix3.replace("</p>", "")

            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, "$prefixFinal\n" +
                        "\n" +
                        "\n" +
                        "\"Ибадати Исламия\" китабынан")
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
    }

    private fun setData(topicId: Int) {
        val models = mutableListOf<BaseModelQAPrefix>()

        if (topicDao.hasPrefix(topicId) == 1) {
            models.add(prefixDao.getPrefixByTopic(topicId))
        }
        models.addAll(questionAnswerDao.getQuestionsByTopic(topicId))

        adapter.models = models
    }

    private fun setFavoriteQuestion(questionAnswer: QuestionAnswer) {
        questionAnswer.isFavorite = 1 - questionAnswer.isFavorite
        questionAnswerDao.updateQuestion(questionAnswer)
    }

    private fun setFavoritePrefix(prefix: Prefix) {
        prefix.isFavorite = 1 - prefix.isFavorite
        prefixDao.updatePrefix(prefix)
    }
}
