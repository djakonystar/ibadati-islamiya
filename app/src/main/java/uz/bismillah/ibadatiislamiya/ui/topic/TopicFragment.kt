package uz.bismillah.ibadatiislamiya.ui.topic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_topic.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BookDatabase
import uz.bismillah.ibadatiislamiya.data.dao.TopicDao
import uz.bismillah.ibadatiislamiya.data.model.QuestionAnswer
import uz.bismillah.ibadatiislamiya.ui.questionanswer.QuestionAnswerFragment
import uz.bismillah.ibadatiislamiya.ui.unit.UnitFragment

class TopicFragment : Fragment(R.layout.fragment_topic) {
    companion object {
        const val TOPIC_ID = "topicId"
        const val TOPIC_NAME = "topicName"
    }

    private val adapter = TopicListAdapter()
    private lateinit var dao: TopicDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topicsRecyclerView.adapter = adapter
        topicsRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        dao = BookDatabase.getInstance(requireContext()).topicDao()
        setData(arguments?.getInt(UnitFragment.UNIT_ID) ?: 1)

        adapter.setOnTopicItemClickListener {
            val bundle = Bundle()
            bundle.putInt(TOPIC_ID, it)
            val fragment = QuestionAnswerFragment()
            fragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.fragmentContainer, fragment)?.commit()
        }
    }

    private fun setData(unitId: Int) {
        adapter.models = dao.getTopicsByUnit(unitId)
    }
}