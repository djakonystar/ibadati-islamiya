package uz.bismillah.ibadatiislamiya.ui.unit

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_unit.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BookDatabase
import uz.bismillah.ibadatiislamiya.data.dao.TopicDao
import uz.bismillah.ibadatiislamiya.data.dao.UnitDao
import uz.bismillah.ibadatiislamiya.ui.SpaceItemDecoration
import uz.bismillah.ibadatiislamiya.ui.questionanswer.QuestionAnswerFragment
import uz.bismillah.ibadatiislamiya.ui.topic.TopicFragment

class UnitFragment : Fragment(R.layout.fragment_unit) {

    companion object {
        const val UNIT_ID = "unitId"
        const val UNIT_NAME = "unitName"
        const val LAST_READ = "last_read"
    }

    private val adapter = UnitListAdapter()
    private lateinit var unitDao: UnitDao
    private lateinit var topicDao: TopicDao
    private lateinit var preferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        unitsRecyclerView.adapter = adapter
        unitsRecyclerView.addItemDecoration(SpaceItemDecoration(8))
        unitDao = BookDatabase.getInstance(requireContext()).unitDao()
        topicDao = BookDatabase.getInstance(requireContext()).topicDao()
        setData()

        preferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)

        if (preferences.getInt(LAST_READ, 0) == 0) {
            lastReadItem.visibility = View.GONE
        } else {
            lastReadItem.visibility = View.VISIBLE
            lastReadTopicName.text = topicDao.getTopicNameById(preferences.getInt(LAST_READ, 1))
        }

        lastReadItem.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(TopicFragment.TOPIC_ID, preferences.getInt(LAST_READ, 1))
            bundle.putString(TopicFragment.TOPIC_NAME, topicDao.getTopicNameById(preferences.getInt(LAST_READ, 1)))
            val fragment = QuestionAnswerFragment()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit()
        }

        adapter.setOnUnitItemClickListener { id, title ->
            val bundle = Bundle()
            bundle.putInt(UNIT_ID, id)
            val fragmentManager = activity?.supportFragmentManager?.beginTransaction()
            if (id != 6) {
                bundle.putString(UNIT_NAME, title)
                val fragment = TopicFragment()
                fragment.arguments = bundle
                fragmentManager?.replace(R.id.fragmentContainer, fragment)?.addToBackStack(null)?.commit()
            } else {
                preferences.edit().putInt(LAST_READ, 133).apply()
                bundle.putInt(TopicFragment.TOPIC_ID, 0)
                bundle.putString(TopicFragment.TOPIC_NAME, topicDao.getTopicNameById(133))
                val fragment = QuestionAnswerFragment()
                fragment.arguments = bundle
                fragmentManager?.replace(R.id.fragmentContainer, fragment)?.addToBackStack(null)?.commit()
            }
        }
    }

    private fun setData() {
        adapter.models = unitDao.getAllUnits()
    }
}