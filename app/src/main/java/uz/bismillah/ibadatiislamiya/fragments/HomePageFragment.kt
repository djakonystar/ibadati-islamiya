package uz.bismillah.ibadatiislamiya.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_homepage.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.unitrv.UnitAdapter
import uz.bismillah.ibadatiislamiya.unitrv.UnitModel

class HomePageFragment : Fragment(R.layout.fragment_homepage) {
    private val units = mutableListOf<UnitModel>()
    private val adapter = UnitAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        unitsRecyclerView.adapter = adapter
        setData()
    }

    private fun setData() {
        repeat(6) {
            units.add(UnitModel(it, "{it + 1}-БОЛИМ"))
        }
        adapter.unitModels = units
    }
}