package uz.bismillah.ibadatiislamiya.ui.questionanswer

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_question_answer.*
import kotlinx.android.synthetic.main.popup_textsize.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BaseModelQAPrefix
import uz.bismillah.ibadatiislamiya.data.BookDatabase
import uz.bismillah.ibadatiislamiya.data.dao.PrefixDao
import uz.bismillah.ibadatiislamiya.data.dao.QuestionAnswerDao
import uz.bismillah.ibadatiislamiya.data.dao.TopicDao
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
    }

    private fun setData(topicId: Int) {
        val models = mutableListOf<BaseModelQAPrefix>()

        if (topicDao.hasPrefix(topicId) == 1) {
            models.add(prefixDao.getPrefixByTopic(topicId))
        }
        models.addAll(questionAnswerDao.getByTopic(topicId))

        adapter.models = models
    }
}
