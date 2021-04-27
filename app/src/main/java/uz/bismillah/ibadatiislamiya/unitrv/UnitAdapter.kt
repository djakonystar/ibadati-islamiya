package uz.bismillah.ibadatiislamiya.unitrv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_homepage.view.*
import uz.bismillah.ibadatiislamiya.R

class UnitAdapter : RecyclerView.Adapter<UnitViewHolder>() {
    var unitModels = listOf<UnitModel>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_unit, parent, false)
        return UnitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UnitViewHolder, position: Int) {
        holder.populateUnit(unitModels[position])
    }

    override fun getItemCount(): Int = unitModels.size

}