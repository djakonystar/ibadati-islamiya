package uz.bismillah.ibadatiislamiya.ui.search

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_search_result.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BookDatabase
import uz.bismillah.ibadatiislamiya.data.dao.QuestionAnswerDao
import uz.bismillah.ibadatiislamiya.data.model.QuestionAnswer

class SearchResultFragment : Fragment(R.layout.fragment_search_result) {
    private val adapter = SearchResultListAdapter()
    private lateinit var questionAnswerDao: QuestionAnswerDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchingResultActionBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        searchingResultRecyclerView.adapter = adapter
        questionAnswerDao = BookDatabase.getInstance(requireContext()).questionAnswerDao()

        val question = arguments?.getString(SearchFragment.QUESTION)

        val result = mutableListOf<QuestionAnswer>()
        result.add(questionAnswerDao.getQuestionByQuestion(question ?: ""))
        adapter.models = result

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
    }

    private fun setFavoriteQuestion(questionAnswer: QuestionAnswer) {
        questionAnswer.isFavorite = 1 - questionAnswer.isFavorite
        questionAnswerDao.updateQuestion(questionAnswer)
    }
}
