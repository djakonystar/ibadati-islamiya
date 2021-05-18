package uz.bismillah.ibadatiislamiya.ui.search

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_search.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BookDatabase
import uz.bismillah.ibadatiislamiya.data.dao.QuestionAnswerDao

class SearchFragment : Fragment(R.layout.fragment_search) {
    companion object {
        const val QUESTION = "questionText"
    }

    private val adapter = SearchListAdapter()
    private lateinit var questionAnswerDao: QuestionAnswerDao
    private var keysShown = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchRecyclerView.adapter = adapter
        searchRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        questionAnswerDao = BookDatabase.getInstance(requireContext()).questionAnswerDao()

        searchEditText.addTextChangedListener {
            val result: List<String> = questionAnswerDao.searchQuestions("%${it.toString()}%")
            adapter.models = result
        }

        searchBar.setEndIconOnClickListener {
            searchEditText.text?.clear()
        }

        adapter.setOnSearchingResultClickListener {
            val bundle = Bundle()
            bundle.putString(QUESTION, it)
            val fragment = SearchResultFragment()
            fragment.arguments = bundle
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit()
        }

        searchActionBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.keys -> {
                    keysShown = !keysShown
                    if (keysShown) {
                        cyrillicKeyPadInSearch.visibility = View.VISIBLE
                    } else {
                        cyrillicKeyPadInSearch.visibility = View.GONE
                    }
                    true
                }
                else -> false
            }
        }

        qrKeyPressed(keyAInSearch)
        qrKeyPressed(keyGInSearch)
        qrKeyPressed(keyHInSearch)
        qrKeyPressed(keyQInSearch)
        qrKeyPressed(keyNInSearch)
        qrKeyPressed(keyOInSearch)
        qrKeyPressed(keyUInSearch)
        qrKeyPressed(keyWInSearch)
    }

    private fun qrKeyPressed(view: View) {
        view.setOnClickListener {
            searchEditText.text?.insert(searchEditText.selectionStart, ((it as Button).text))
        }
    }
}
