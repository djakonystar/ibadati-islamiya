package uz.bismillah.ibadatiislamiya.ui.topic

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_topic.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BookDatabase
import uz.bismillah.ibadatiislamiya.data.dao.TopicDao
import uz.bismillah.ibadatiislamiya.data.model.Topic
import uz.bismillah.ibadatiislamiya.ui.questionanswer.QuestionAnswerFragment
import uz.bismillah.ibadatiislamiya.ui.unit.UnitFragment

class TopicFragment : Fragment(R.layout.fragment_topic) {
    companion object {
        const val TOPIC_ID = "topicId"
        const val TOPIC_NAME = "topicName"
    }

    private val adapter = TopicListAdapter()
    private lateinit var dao: TopicDao
    private lateinit var preferences: SharedPreferences
    private var keysShown = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)

        topicActionBar.title = arguments?.getString(UnitFragment.UNIT_NAME)
        topicActionBar.setNavigationIcon(R.drawable.ic_back_24)
        topicActionBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        topicsRecyclerView.adapter = adapter
        topicsRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        dao = BookDatabase.getInstance(requireContext()).topicDao()
        setData(arguments?.getInt(UnitFragment.UNIT_ID) ?: 1)

        adapter.setOnTopicItemClickListener { id, title ->
            preferences.edit().putInt(UnitFragment.LAST_READ, id).apply()
            val bundle = Bundle()
            bundle.putInt(TOPIC_ID, id)
            bundle.putString(TOPIC_NAME, title)
            val fragment = QuestionAnswerFragment()
            fragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.fragmentContainer, fragment)?.commit()
        }

        topicSearchEditText.addTextChangedListener {
            val result : List<Topic> = dao.searchTopicByName((arguments?.getInt(UnitFragment.UNIT_ID) ?: 1), "%${it.toString()}%")
            adapter.models = result
        }

        topicSearchBar.setEndIconOnClickListener {
            topicSearchEditText.text?.clear()
        }

        topicActionBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.keys -> {
                    keysShown = !keysShown
                    if (keysShown) {
                        cyrillicKeyPad.visibility = View.VISIBLE
                    } else {
                        cyrillicKeyPad.visibility = View.GONE
                    }
                    true
                }
                else -> false
            }
        }

        qrKeyPressed(keyA)
        qrKeyPressed(keyG)
        qrKeyPressed(keyH)
        qrKeyPressed(keyQ)
        qrKeyPressed(keyN)
        qrKeyPressed(keyO)
        qrKeyPressed(keyU)
        qrKeyPressed(keyW)

    }

    private fun setData(unitId: Int) {
        adapter.models = dao.getTopicsByUnit(unitId)
    }

    private fun qrKeyPressed(view: View) {
        view.setOnClickListener {
            topicSearchEditText.text?.insert(topicSearchEditText.selectionStart, ((it as Button).text))
        }
    }
}