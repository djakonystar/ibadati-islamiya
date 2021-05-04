package uz.bismillah.ibadatiislamiya.ui.unit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_unit.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.data.BookDatabase
import uz.bismillah.ibadatiislamiya.data.dao.UnitDao
import uz.bismillah.ibadatiislamiya.ui.SpaceItemDecoration
import uz.bismillah.ibadatiislamiya.ui.topic.TopicFragment

class UnitFragment : Fragment(R.layout.fragment_unit) {

    companion object {
        const val UNIT_ID = "unitId"
    }

    private val adapter = UnitListAdapter()
    private lateinit var dao: UnitDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unitsRecyclerView.adapter = adapter
        unitsRecyclerView.addItemDecoration(SpaceItemDecoration(8))
        dao = BookDatabase.getInstance(requireContext()).unitDao()
        setData()

        adapter.setOnUnitItemClickListener {
            val bundle = Bundle()
            val fragment = TopicFragment()
            bundle.putInt("unitId", it)
            fragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer, fragment)?.addToBackStack(null)?.commit()
        }
    }

    private fun setData() {
        adapter.models = dao.getAllUnits()
    }
}