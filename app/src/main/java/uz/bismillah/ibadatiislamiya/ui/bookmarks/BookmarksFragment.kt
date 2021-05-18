package uz.bismillah.ibadatiislamiya.ui.bookmarks

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_bookmarks.*
import kotlinx.android.synthetic.main.fragment_question_answer.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BaseModelQAPrefix
import uz.bismillah.ibadatiislamiya.data.BookDatabase
import uz.bismillah.ibadatiislamiya.data.dao.PrefixDao
import uz.bismillah.ibadatiislamiya.data.dao.QuestionAnswerDao
import uz.bismillah.ibadatiislamiya.data.model.Prefix
import uz.bismillah.ibadatiislamiya.data.model.QuestionAnswer

class BookmarksFragment : Fragment(R.layout.fragment_bookmarks) {
    private val adapter = BookmarksListAdapter()
    private lateinit var questionAnswerDao: QuestionAnswerDao
    private lateinit var prefixDao: PrefixDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookmarksRecyclerView.adapter = adapter
        questionAnswerDao = BookDatabase.getInstance(requireContext()).questionAnswerDao()
        prefixDao = BookDatabase.getInstance(requireContext()).prefixDao()
        setData()

        bookmarksToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.textSizeSettings) {
                val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView = inflater.inflate(R.layout.popup_textsize, null)

                val width = LinearLayout.LayoutParams.WRAP_CONTENT
                val height = LinearLayout.LayoutParams.WRAP_CONTENT
                val focusable = true
                val popupWindow = PopupWindow(popupView, width, height, focusable)
                popupWindow.showAsDropDown(bookmarksAppBar,0, 10, Gravity.END)

                true
            } else {
                false
            }
        }

        adapter.setOnQuestionFavIconClickListener { questionAnswer, position ->
            setFavoriteQuestion(questionAnswer)
            adapter.removeItem(position)
        }

        adapter.setOnPrefixFavIconClickListener { prefix, position ->
            setFavoritePrefix(prefix)
            adapter.removeItem(position)
        }
    }

    private fun setData() {
        val models = mutableListOf<BaseModelQAPrefix>()

        models.addAll(prefixDao.getAllFavoritePrefixes())
        models.addAll(questionAnswerDao.getAllFavoriteQuestions())

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