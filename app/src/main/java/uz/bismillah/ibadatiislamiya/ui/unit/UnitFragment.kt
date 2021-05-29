package uz.bismillah.ibadatiislamiya.ui.unit

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_unit.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BookDatabase
import uz.bismillah.ibadatiislamiya.data.dao.TopicDao
import uz.bismillah.ibadatiislamiya.data.dao.UnitDao
import uz.bismillah.ibadatiislamiya.ui.MainActivity
import uz.bismillah.ibadatiislamiya.ui.SpaceItemDecoration
import uz.bismillah.ibadatiislamiya.ui.questionanswer.QuestionAnswerFragment
import uz.bismillah.ibadatiislamiya.ui.topic.TopicFragment

class UnitFragment : Fragment(R.layout.fragment_unit) {

    companion object {
        const val UNIT_ID = "unitId"
        const val UNIT_NAME = "unitName"
        const val LAST_READ = "last_read"
        const val CURRENT_THEME = "currentTheme"
    }

    private val adapter = UnitListAdapter()
    private lateinit var unitDao: UnitDao
    private lateinit var topicDao: TopicDao
    private lateinit var preferences: SharedPreferences
    private var lastReadTopicId = 0
    private lateinit var lastReadTopic: String
    private var currentTheme: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        unitDao = BookDatabase.getInstance(requireContext()).unitDao()
        topicDao = BookDatabase.getInstance(requireContext()).topicDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        currentTheme = preferences.getInt(CURRENT_THEME, 1)

        lastReadTopicId = preferences.getInt(LAST_READ, 0)
        lastReadTopic = topicDao.getTopicNameById(lastReadTopicId)

        unitsRecyclerView.adapter = adapter
        unitsRecyclerView.addItemDecoration(SpaceItemDecoration(8))
        setData()

        if (lastReadTopicId == 0) {
            lastReadItem.visibility = View.GONE
        } else {
            lastReadItem.visibility = View.VISIBLE
            lastReadTopicName.text = lastReadTopic
        }

        lastReadItem.setOnClickListener {
            findNavController().navigate(
                UnitFragmentDirections.actionUnitFragmentToQuestionAnswerFragment(lastReadTopicId, lastReadTopic)
            )
        }

        adapter.setOnUnitItemClickListener { id, title ->
            if (id != 6) {
                findNavController().navigate(
                    UnitFragmentDirections.actionUnitFragmentToTopicFragment(id, title)
                )
            } else {
                preferences.edit().putInt(LAST_READ, 133).apply()

                findNavController().navigate(
                    UnitFragmentDirections.actionUnitFragmentToQuestionAnswerFragment(133, topicDao.getTopicNameById(133))
                )
            }
        }
    }

    // TODO: Theme changer
    /* override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_actionbar_with_theme_changer, menu)
        menu.findItem(R.id.toggleTheme).setOnMenuItemClickListener {
            if (AppCompatDelegate.MODE_NIGHT_YES == preferences.getInt(CURRENT_THEME, AppCompatDelegate.MODE_NIGHT_NO)) {
                it.icon = resources.getDrawable(R.drawable.ic_theme_light_24)
                preferences.edit().putInt(CURRENT_THEME, AppCompatDelegate.MODE_NIGHT_NO).apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            else {
                it.icon = resources.getDrawable(R.drawable.ic_theme_night_24)
                preferences.edit().putInt(CURRENT_THEME, AppCompatDelegate.MODE_NIGHT_YES).apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            true
        }
    } */

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.toggleTheme -> {
//                when {
//                    preferences.getInt(CURRENT_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) ==
//                            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
//                        item.icon = resources.getDrawable(R.drawable.ic_theme_light_24)
//                        preferences.edit().putInt(CURRENT_THEME, AppCompatDelegate.MODE_NIGHT_YES).apply()
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    }
//                    preferences.getInt(CURRENT_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) ==
//                            AppCompatDelegate.MODE_NIGHT_YES -> {
//                        item.icon = resources.getDrawable(R.drawable.ic_theme_auto_24)
//                        preferences.edit().putInt(CURRENT_THEME, AppCompatDelegate.MODE_NIGHT_NO).apply()
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    }
//                    else -> {
//                        item.icon = resources.getDrawable(R.drawable.ic_theme_night_24)
//                        preferences.edit().putInt(CURRENT_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//                    }
//                }
//                true
//            }
//            else -> false
//        }
//    }

    private fun setData() {
        adapter.models = unitDao.getAllUnits()
    }
}
